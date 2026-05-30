#include "MqttManager.h"
#include "esp_event.h"
#include "esp_log.h"
#include "nvs_flash.h"
#include "esp_wifi.h"
#include "esp_netif.h"
#include "IrSender.h"

extern QueueHandle_t displayFrameQueue;

void irSendTask(void *pv);

static auto TAG = "MQTT";
static constexpr auto STATE_TOPIC = "smartac/state";
static constexpr auto COMMAND_TOPIC = "smartac/command";
static constexpr auto STATUS_TOPIC = "smartac/status";
static constexpr auto ONLINE_STATUS = "online";
static constexpr auto OFFLINE_STATUS = "offline";

void MqttManager::start(const char *ssid, const char *pass, const char *broker, const uint32_t port) {
    esp_log_level_set(TAG, ESP_LOG_INFO);

    wifiInit(ssid, pass);
    mqttInit(broker, port);
}

void MqttManager::wifiInit(const char *ssid, const char *pass) {
    nvs_flash_init();

    ESP_ERROR_CHECK(esp_netif_init());
    ESP_ERROR_CHECK(esp_event_loop_create_default());

    esp_netif_create_default_wifi_sta();

    wifi_init_config_t cfg = WIFI_INIT_CONFIG_DEFAULT();
    ESP_ERROR_CHECK(esp_wifi_init(&cfg));

    ESP_ERROR_CHECK(esp_event_handler_instance_register(
        WIFI_EVENT,
        ESP_EVENT_ANY_ID,
        &MqttManager::wifiEventHandler,
        nullptr,
        nullptr
    ));
    ESP_ERROR_CHECK(esp_event_handler_instance_register(
        IP_EVENT,
        ESP_EVENT_ANY_ID,
        &MqttManager::ipEventHandler,
        nullptr,
        nullptr
    ));

    wifi_config_t wifi_config = {};
    strncpy(reinterpret_cast<char *>(wifi_config.sta.ssid), ssid, sizeof(wifi_config.sta.ssid));
    strncpy(reinterpret_cast<char *>(wifi_config.sta.password), pass, sizeof(wifi_config.sta.password));

    wifi_config.sta.threshold.authmode = WIFI_AUTH_WPA2_PSK;
    wifi_config.sta.sae_pwe_h2e = WPA3_SAE_PWE_BOTH;

    ESP_ERROR_CHECK(esp_wifi_set_mode(WIFI_MODE_STA));
    ESP_ERROR_CHECK(esp_wifi_set_config(WIFI_IF_STA, &wifi_config));
    ESP_ERROR_CHECK(esp_wifi_start());

    ESP_ERROR_CHECK(esp_wifi_connect());
}

void MqttManager::mqttInit(const char *broker, const uint32_t port) {
    esp_mqtt_client_config_t cfg = {};

    cfg.broker.address.hostname = broker;
    cfg.broker.address.transport = MQTT_TRANSPORT_OVER_TCP;
    cfg.broker.address.port = port;

    cfg.session.protocol_ver = MQTT_PROTOCOL_V_5;
    cfg.session.keepalive = 10;
    cfg.session.last_will.topic = STATUS_TOPIC;
    cfg.session.last_will.msg = OFFLINE_STATUS;
    cfg.session.last_will.msg_len = strlen(OFFLINE_STATUS);
    cfg.session.last_will.qos = 1;
    cfg.session.last_will.retain = true;

    client = esp_mqtt_client_init(&cfg);
    ESP_ERROR_CHECK(esp_mqtt_client_register_event(
        client,
        MQTT_EVENT_ANY,
        mqttEventHandler,
        nullptr
    ));

    ESP_ERROR_CHECK(esp_mqtt_client_start(client));
}

void MqttManager::mqttEventHandler(void *handler_args, esp_event_base_t base, int32_t event_id, void *event_data) {
    auto *event = static_cast<esp_mqtt_event_handle_t>(event_data);

    switch (event_id) {
        case MQTT_EVENT_CONNECTED:
            ESP_LOGI(TAG, "Connected to MQTT broker");

            connected = true;

            esp_mqtt_client_subscribe(event->client, COMMAND_TOPIC, 1);
            esp_mqtt_client_publish(event->client, STATUS_TOPIC,
                                    ONLINE_STATUS, 0, 1, true);
            break;

        case MQTT_EVENT_DISCONNECTED:
            ESP_LOGI(TAG, "Disconnected from MQTT broker");

            connected = false;

            break;

        case MQTT_EVENT_DATA:
            ESP_LOGI(TAG, "MQTT RX");

            handleCommandMessage(
                reinterpret_cast<const uint8_t *>(event->data),
                event->data_len
            );
            break;
        default: ;
    }
}

void MqttManager::handleCommandMessage(const uint8_t *data, const size_t len) {
    if (len != IR_DATA_SIZE) {
        ESP_LOGW(TAG, "Invalid IR command length: %u", len);
        return;
    }

    auto *buf = static_cast<uint8_t *>(malloc(IR_DATA_SIZE));

    memcpy(buf, data, len);

    xTaskCreatePinnedToCore(
        irSendTask,
        "irSendTask",
        4096,
        buf,
        5,
        nullptr,
        1 // Core 1 → IR tasks
    );
}

void MqttManager::wifiEventHandler(void *arg, esp_event_base_t event_base, int32_t event_id, void *event_data) {
    if (event_base != WIFI_EVENT) return;

    switch (event_id) {
        case WIFI_EVENT_STA_START:
            ESP_LOGI(TAG, "WiFi STA start, connecting...");
            esp_wifi_connect();
            break;
        case WIFI_EVENT_STA_CONNECTED:
            wifiReady = true;
            ESP_LOGI(TAG, "WiFi connected");
            break;
        case WIFI_EVENT_STA_DISCONNECTED: {
            wifiReady = false;
            ipReady = false;
            connected = false;
            auto *disc = static_cast<wifi_event_sta_disconnected_t *>(event_data);
            int reason = disc ? disc->reason : -1;
            ESP_LOGW(TAG, "WiFi disconnected (reason %d), retrying...", reason);
            if (client != nullptr) {
                esp_mqtt_client_disconnect(client);
            }
            esp_wifi_connect();
            break;
        }
        default:
            break;
    }
}

void MqttManager::ipEventHandler(void *arg, esp_event_base_t event_base, int32_t event_id, void *event_data) {
    if (event_base != IP_EVENT) return;

    switch (event_id) {
        case IP_EVENT_STA_GOT_IP:
            ipReady = true;
            ESP_LOGI(TAG, "IP acquired, resuming MQTT");
            if (client != nullptr) {
                esp_mqtt_client_reconnect(client);
            }
            break;
        case IP_EVENT_STA_LOST_IP:
            ipReady = false;
            connected = false;
            ESP_LOGW(TAG, "IP lost, awaiting reconnection");
            if (client != nullptr) {
                esp_mqtt_client_disconnect(client);
            }
            break;
        default:
            break;
    }
}

void MqttManager::publishState(const uint8_t *frame, const size_t len) {
    if (client == nullptr || !connected || !ipReady) return;

    esp_mqtt_client_publish(client, STATE_TOPIC,
                            reinterpret_cast<const char *>(frame), len,
                            0, false
    );
}
