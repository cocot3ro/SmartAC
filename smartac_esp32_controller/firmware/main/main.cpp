#include <freertos/FreeRTOS.h>
#include <esp_log.h>
#include <esp_wifi.h>
#include <esp_mac.h>
#include <esp_event.h>
#include <nvs_flash.h>
#include <optional>
#include <qrcode.h>
#include <sdkconfig.h>

#include <network_provisioning/manager.h>
#include <network_provisioning/scheme_softap.h>
#include "esp_timer.h"

#include "ota.h"
#include "device_config.h"
#include "display_reader.h"
#include "ir_frame_t.h"
#include "ir_emitter.h"
#include "mqtt_manager.h"

constexpr auto TAG = "APP";

constexpr int WIFI_CONNECTED_EVENT = BIT0;
static EventGroupHandle_t wifi_event_group;

static esp_timer_handle_t ota_timer;

static std::optional<MqttManager> mqtt_mgr;
static std::optional<DisplayReader> display_reader;
static std::optional<IrEmitter> ir_emitter;

static QueueHandle_t ir_cmd_queue;
static QueueHandle_t display_frame_queue;

#define PROV_QR_VERSION         "v1"
#define PROV_TRANSPORT_SOFTAP   "softap"
#define QRCODE_BASE_URL         "https://espressif.github.io/esp-jumpstart/qrcode.html"

static void network_prov_event_handler(
    void *arg,
    esp_event_base_t event_base,
    const int32_t event_id,
    void *event_data
) {
    switch (event_id) {
        case NETWORK_PROV_START:
            ESP_LOGI(TAG, "Provisioning started");
            break;

        case NETWORK_PROV_WIFI_CRED_RECV: {
            ESP_LOGD(
                TAG,
                "Received Wi-Fi credentials"
                "\n\tSSID     : %s\n\tPassword : %s",
                reinterpret_cast<const char *>(static_cast<wifi_sta_config_t *>(event_data)->ssid),
                reinterpret_cast<const char *>(static_cast<wifi_sta_config_t *>(event_data)->password)
            );
            break;
        }

        case NETWORK_PROV_WIFI_CRED_FAIL: {
            const auto *reason = static_cast<network_prov_wifi_sta_fail_reason_t *>(event_data);

            ESP_LOGE(TAG, "Provisioning failed!\n\tReason : %s"
                     "\n\tPlease reset to factory and retry provisioning",
                     *reason == NETWORK_PROV_WIFI_STA_AUTH_ERROR ?
                     "Wi-Fi station authentication failed" : "Wi-Fi access-point not found");
            break;
        }

        case NETWORK_PROV_WIFI_CRED_SUCCESS:
            ESP_LOGI(TAG, "Provisioning successful");
            break;

        case NETWORK_PROV_END: {
            const esp_err_t err = network_prov_mgr_deinit();
            if (err != ESP_OK) {
                ESP_LOGE(TAG, "Failed to de-initialize provisioning manager: %s", esp_err_to_name(err));
            }
            break;
        }

        default:
            ESP_LOGI(TAG, "network_prov_event_handler: default case, event_id: %d", event_id);
            break;
    }
}

static void wifi_event_handler(
    void *arg,
    esp_event_base_t event_base,
    const int32_t event_id,
    void *event_data
) {
    switch (event_id) {
        case WIFI_EVENT_STA_START:
            ESP_LOGI(TAG, "WIFI_EVENT_STA_START");
            esp_wifi_connect();
            break;

        case WIFI_EVENT_STA_STOP:
            ESP_LOGI(TAG, "WIFI_EVENT_STA_STOP");
            break;

        case WIFI_EVENT_STA_DISCONNECTED:
            ESP_LOGI(TAG, "Disconnected. Connecting to the AP again...");
            esp_wifi_connect();
            break;

        case WIFI_EVENT_AP_STACONNECTED:
            ESP_LOGI(TAG, "SoftAP transport: Connected!");
            break;

        case WIFI_EVENT_AP_STADISCONNECTED:
            ESP_LOGI(TAG, "SoftAP transport: Disconnected!");
            break;

        default:
            ESP_LOGI(TAG, "wifi_event_handler: default case, event_id: %d", event_id);
            break;
    }
}

static void ip_event_handler(
    void *arg,
    esp_event_base_t event_base,
    const int32_t event_id,
    void *event_data
) {
    switch (event_id) {
        case IP_EVENT_STA_GOT_IP: {
            ESP_LOGI(TAG, "ip_event_handler: Got IP address");
            xEventGroupSetBits(wifi_event_group, WIFI_CONNECTED_EVENT);
            run_ota_task();
            ESP_ERROR_CHECK(esp_timer_start_periodic(ota_timer, CONFIG_OTA_TIMER_DELAY_S * 1000000LL));
            break;
        }

        case IP_EVENT_NETIF_DOWN:
            ESP_LOGI(TAG, "ip_event_handler: Lost WiFi connection, stopping OTA timer");
            ESP_ERROR_CHECK(esp_timer_stop(ota_timer));
            break;

        default:
            ESP_LOGI(TAG, "ip_event_handler: default case, event_id: %d", event_id);
            break;
    }
}

static void wifi_prov_print_qr(const char *ap_name) {
    char payload[150] = {0};

    snprintf(
        payload,
        sizeof(payload),
        R"({"ver":"%s","name":"%s","pop":"%s","transport":"%s"})",
        PROV_QR_VERSION,
        ap_name,
        DeviceConfig::get_proof_of_possession(),
        PROV_TRANSPORT_SOFTAP
    );

    ESP_LOGI(TAG, "Scan this QR code from the provisioning application for Provisioning.");
    auto cfg = ESP_QRCODE_CONFIG_DEFAULT();
    esp_qrcode_generate(&cfg, payload);

    ESP_LOGI(
        TAG,
        "If QR code is not visible, copy paste the below URL in a browser.\n%s?data=%s",
        QRCODE_BASE_URL,
        payload
    );
}

[[noreturn]]
static void mqtt_task(void *pvParameters) {
    mqtt_mgr.emplace(
        &ir_cmd_queue, &display_frame_queue,
        DeviceConfig::get_mqtt_host(),
        DeviceConfig::get_mqtt_port(),
        DeviceConfig::get_mqtt_user(),
        DeviceConfig::get_mqtt_password()
    );

    mqtt_mgr->init();
}

[[noreturn]]
static void display_reader_task(void *pvParameters) {
    display_reader.emplace(
        UART_NUM_1,
        static_cast<gpio_num_t>(CONFIG_UART_RX_PIN),
        CONFIG_UART_BAUD_RATE,
        &display_frame_queue
    );

    display_reader->read_loop();
}

[[noreturn]]
static void ir_emitter_task(void *pvParameters) {
    ir_emitter.emplace(
        static_cast<gpio_num_t>(CONFIG_IR_TX_PIN),
        CONFIG_IR_CARRIER_FREQ_HZ
    );

    while (true) {
        ir_frame_t frame{};
        if (xQueueReceive(ir_cmd_queue, &frame, portMAX_DELAY) == pdTRUE) {
            ir_emitter->send_ir_frame(&frame);
        }
    }
}

extern "C" void app_main(void) {
    ESP_ERROR_CHECK(nvs_flash_init());
    ESP_ERROR_CHECK(nvs_flash_init_partition(CONFIG_NVS_PARTITION_NAME));

    ESP_ERROR_CHECK(esp_netif_init());
    ESP_ERROR_CHECK(esp_event_loop_create_default());
    wifi_event_group = xEventGroupCreate();

    ESP_ERROR_CHECK(esp_event_handler_register(
        NETWORK_PROV_EVENT, ESP_EVENT_ANY_ID, &network_prov_event_handler, nullptr
    ));
    ESP_ERROR_CHECK(esp_event_handler_register(IP_EVENT, ESP_EVENT_ANY_ID, &ip_event_handler, nullptr));
    ESP_ERROR_CHECK(esp_event_handler_register(WIFI_EVENT, ESP_EVENT_ANY_ID, &wifi_event_handler, nullptr));

    esp_netif_create_default_wifi_sta();
    esp_netif_create_default_wifi_ap();

    const wifi_init_config_t cfg = WIFI_INIT_CONFIG_DEFAULT();
    ESP_ERROR_CHECK(esp_wifi_init(&cfg));

    /* Configuration for the provisioning manager */
    network_prov_mgr_config_t config = {};
    config.scheme = network_prov_scheme_softap;
    config.scheme_event_handler = NETWORK_PROV_EVENT_HANDLER_NONE;

    ESP_ERROR_CHECK(network_prov_mgr_init(config));
    bool provisioned = false;
    ESP_ERROR_CHECK(network_prov_mgr_is_wifi_provisioned(&provisioned));

    constexpr esp_timer_create_args_t timer_args = {
        .callback = ota_timer_callback,
        .arg = nullptr,
        .dispatch_method = ESP_TIMER_TASK,
        .name = "ota_timer",
        .skip_unhandled_events = true
    };

    ESP_ERROR_CHECK(esp_timer_create(&timer_args, &ota_timer));

    if (!provisioned) {
        ESP_LOGI(TAG, "Starting provisioning");

        uint8_t mac[6];

        ESP_ERROR_CHECK(esp_read_mac(mac, ESP_MAC_WIFI_STA));

        char ap_name[32];

        snprintf(
            ap_name,
            sizeof(ap_name),
            "SmartAC-%02X%02X%02X",
            mac[3],
            mac[4],
            mac[5]
        );

        ESP_ERROR_CHECK(
            network_prov_mgr_start_provisioning(
                NETWORK_PROV_SECURITY_1,
                DeviceConfig::get_proof_of_possession(),
                ap_name,
                nullptr
            )
        );

        wifi_prov_print_qr(ap_name);
    } else {
        ESP_LOGI(TAG, "Already provisioned, starting Wi-Fi STA");
        ESP_ERROR_CHECK(network_prov_mgr_deinit());
        ESP_ERROR_CHECK(esp_wifi_set_mode(WIFI_MODE_STA));
        ESP_ERROR_CHECK(esp_wifi_start());
    }

    xEventGroupWaitBits(wifi_event_group, WIFI_CONNECTED_EVENT, true, true, portMAX_DELAY);

    /* Start main application now */

    ir_cmd_queue = xQueueCreate(16, IR_FRAME_SIZE);
    display_frame_queue = xQueueCreate(16, DISPLAY_FRAME_SIZE);

    xTaskCreate(mqtt_task, "mqtt_task", 8192, nullptr, 5, nullptr);
    xTaskCreate(display_reader_task, "display_reader_task", 8192, nullptr, 5, nullptr);
    xTaskCreate(ir_emitter_task, "ir_emitter_task", 8192, nullptr, 5, nullptr);
}
