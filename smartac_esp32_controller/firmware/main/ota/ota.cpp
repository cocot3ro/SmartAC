#include <sys/param.h>
#include "esp_crt_bundle.h"
#include "esp_https_ota.h"
#include "esp_log.h"
#include "esp_wifi.h"
#include "esp_tls.h"
#include "cJSON.h"
#include "esp_timer.h"
#include "esp_http_server.h"
#include "sdkconfig.h"

#include "ota.h"

#include "device_config.h"
#include "version.h"

constexpr auto TAG = "OTA";

#define MAX_HTTP_OUTPUT_BUFFER 2048

static TaskHandle_t xOtaTaskHandler;
static bool ota_task_running = false;

static esp_err_t ota_http_event_handler(esp_http_client_event_t *evt) {
    switch (evt->event_id) {
        case HTTP_EVENT_ERROR:
            ESP_LOGD(TAG, "ota_http_event_handler HTTP_EVENT_ERROR");
            break;
        case HTTP_EVENT_ON_CONNECTED:
            ESP_LOGD(TAG, "ota_http_event_handler HTTP_EVENT_ON_CONNECTED");
            break;
        case HTTP_EVENT_HEADER_SENT:
            ESP_LOGD(TAG, "ota_http_event_handler HTTP_EVENT_HEADER_SENT");
            break;
        case HTTP_EVENT_ON_HEADER:
            ESP_LOGD(TAG, "ota_http_event_handler HTTP_EVENT_ON_HEADER, key=%s, value=%s", evt->header_key,
                     evt->header_value);
            break;
        case HTTP_EVENT_ON_HEADERS_COMPLETE:
            ESP_LOGD(TAG, "ota_http_event_handler HTTP_EVENT_ON_HEADERS_COMPLETE");
            break;
        case HTTP_EVENT_ON_DATA:
            ESP_LOGD(TAG, "ota_http_event_handler HTTP_EVENT_ON_DATA, len=%d", evt->data_len);
            break;
        case HTTP_EVENT_ON_FINISH:
            ESP_LOGD(TAG, "ota_http_event_handler HTTP_EVENT_ON_FINISH");
            break;
        case HTTP_EVENT_DISCONNECTED:
            ESP_LOGD(TAG, "ota_http_event_handler HTTP_EVENT_DISCONNECTED");
            break;
        case HTTP_EVENT_REDIRECT:
            ESP_LOGD(TAG, "ota_http_event_handler HTTP_EVENT_REDIRECT");
            break;
        default:
            break;
    }

    return ESP_OK;
}

static esp_err_t http_event_handler(esp_http_client_event_t *evt) {
    static char *output_buffer; // Buffer to store response of http request from event handler
    static int output_len; // Stores number of bytes read
    switch (evt->event_id) {
        case HTTP_EVENT_ERROR:
            ESP_LOGD(TAG, "http_event_handler HTTP_EVENT_ERROR");
            break;

        case HTTP_EVENT_ON_CONNECTED:
            ESP_LOGD(TAG, "http_event_handler HTTP_EVENT_ON_CONNECTED");
            break;

        case HTTP_EVENT_HEADER_SENT:
            ESP_LOGD(TAG, "http_event_handler HTTP_EVENT_HEADER_SENT");
            break;

        case HTTP_EVENT_ON_HEADER:
            ESP_LOGD(TAG, "http_event_handler HTTP_EVENT_ON_HEADER, key=%s, value=%s", evt->header_key,
                     evt->header_value);
            break;

        case HTTP_EVENT_ON_HEADERS_COMPLETE:
            ESP_LOGD(TAG, "http_event_handler HTTP_EVENT_ON_HEADERS_COMPLETE");
            break;

        case HTTP_EVENT_ON_DATA:
            ESP_LOGD(TAG, "http_event_handler HTTP_EVENT_ON_DATA, len=%d", evt->data_len);
            // Clean the buffer in case of a new request
            if (output_len == 0 && evt->user_data) {
                // we are just starting to copy the output data into the use
                memset(evt->user_data, 0, MAX_HTTP_OUTPUT_BUFFER);
            }
            /*
             *  Check for chunked encoding is added as the URL for chunked encoding used in this example returns binary data.
             *  However, event handler can also be used in case chunked encoding is used.
             */
            if (!esp_http_client_is_chunked_response(evt->client)) {
                // If user_data buffer is configured, copy the response into the buffer
                int copy_len = 0;
                if (evt->user_data) {
                    // The last byte in evt->user_data is kept for the nullptr character in case of out-of-bound access.
                    copy_len = MIN(evt->data_len, (MAX_HTTP_OUTPUT_BUFFER - output_len));
                    if (copy_len) {
                        memcpy(static_cast<char *>(evt->user_data) + output_len, evt->data, copy_len);
                    }
                } else {
                    const int64_t content_len = esp_http_client_get_content_length(evt->client);
                    if (output_buffer == nullptr) {
                        // We initialize output_buffer with 0 because it is used by strlen() and similar functions therefore should be null terminated.
                        output_buffer = static_cast<char *>(calloc(content_len + 1, sizeof(char)));
                        output_len = 0;
                        if (output_buffer == nullptr) {
                            ESP_LOGE(TAG, "Failed to allocate memory for output buffer");
                            return ESP_FAIL;
                        }
                    }
                    copy_len = MIN(evt->data_len, (content_len - output_len));
                    if (copy_len) {
                        memcpy(output_buffer + output_len, evt->data, copy_len);
                    }
                }
                output_len += copy_len;
            }

            break;

        case HTTP_EVENT_ON_FINISH:
            ESP_LOGD(TAG, "http_event_handler HTTP_EVENT_ON_FINISH");
            if (output_buffer != nullptr) {
                free(output_buffer);
                output_buffer = nullptr;
            }
            output_len = 0;
            break;

        case HTTP_EVENT_DISCONNECTED: {
            ESP_LOGI(TAG, "http_event_handler HTTP_EVENT_DISCONNECTED");
            int mbedtls_err = 0;
            const esp_err_t err = esp_tls_get_and_clear_last_error(
                static_cast<esp_tls_error_handle_t>(evt->data),
                &mbedtls_err,
                nullptr
            );
            if (err != 0) {
                ESP_LOGI(TAG, "Last esp error code: 0x%x", err);
                ESP_LOGI(TAG, "Last mbedtls failure: 0x%x", mbedtls_err);
            }
            if (output_buffer != nullptr) {
                free(output_buffer);
                output_buffer = nullptr;
            }
            output_len = 0;
            break;
        }

        case HTTP_EVENT_REDIRECT:
            ESP_LOGD(TAG, "http_event_handler HTTP_EVENT_REDIRECT");
            break;

        default:
            break;
    }

    return ESP_OK;
}

static void check_for_updates() {
    ESP_LOGI(TAG, "Starting OTA check");

    char local_response_buffer[MAX_HTTP_OUTPUT_BUFFER + 1] = {0};

    esp_http_client_config_t apiConfig = {};
    apiConfig.transport_type = HTTP_TRANSPORT_OVER_SSL;
    apiConfig.host = DeviceConfig::get_backend_host();
    apiConfig.path = "/api/v1/ota/check";
    apiConfig.method = HTTP_METHOD_GET;
    apiConfig.crt_bundle_attach = esp_crt_bundle_attach;
    apiConfig.event_handler = http_event_handler;
    apiConfig.user_data = &local_response_buffer;
    apiConfig.username = DeviceConfig::get_device_code();
    apiConfig.password = DeviceConfig::get_api_key();
    apiConfig.auth_type = HTTP_AUTH_TYPE_DIGEST;

    esp_http_client_handle_t client = esp_http_client_init(&apiConfig);

    int64_t start = esp_timer_get_time();
    esp_err_t err = esp_http_client_perform(client);
    int64_t end = esp_timer_get_time();
    ESP_LOGI(TAG, "Calling %s took %lld ms", apiConfig.path, (end - start) / 1000);

    if (err != ESP_OK || esp_http_client_get_status_code(client) != 200) {
        ESP_LOGE(
            TAG,
            "HTTP GET request failed: %s, status code: %d",
            esp_err_to_name(err),
            esp_http_client_get_status_code(client)
        );
        esp_http_client_cleanup(client);
        return;
    }

    ESP_LOGI(
        TAG,
        "HTTP GET Status = %d, content_length = %d",
        esp_http_client_get_status_code(client),
        esp_http_client_get_content_length(client)
    );
    esp_http_client_cleanup(client);

    cJSON *root = cJSON_Parse(local_response_buffer);

    const char *remote_version = cJSON_GetObjectItem(root, "version")->valuestring;
    const char *ota_url = cJSON_GetObjectItem(root, "download_url")->valuestring;

    Version remote = {};
    if (!remote.parse(remote_version)) {
        ESP_LOGE(TAG, "Failed to parse remote version %s, parsed: major = %d, minor = %d, patch = %d",
                 remote_version, remote.major, remote.minor, remote.patch);
        return;
    }

    const esp_app_desc_t *app = esp_app_get_description();
    Version local = {};
    if (!local.parse(app->version)) {
        ESP_LOGE(TAG, "Failed to parse local version %s, parsed: major = %d, minor = %d, patch = %d",
                 app->version, local.major, local.minor, local.patch);
        return;
    }

    if (!(remote > local)) {
        ESP_LOGI(TAG, "Up to date");
        return;
    }

    esp_http_client_config_t config = {};
    config.url = ota_url;
    config.event_handler = ota_http_event_handler;
    config.crt_bundle_attach = esp_crt_bundle_attach;

    esp_https_ota_config_t ota_config = {};
    ota_config.http_config = &config;

    esp_err_t ret = esp_https_ota(&ota_config);
    if (ret != ESP_OK) {
        ESP_LOGE(TAG, "Error %s", esp_err_to_name(ret));
    } else {
        ESP_LOGI(TAG, "OTA completed. Rebooting...");
        esp_restart();
    }
}


static void ota_task_callback(void *pvParameters) {
    ota_task_running = true;
    check_for_updates();

    ota_task_running = false;
    vTaskDelete(xOtaTaskHandler);
}

void run_ota_task() {
    if (ota_task_running) {
        ESP_LOGI(TAG, "OTA task is already running. Cancelling new call ...");
        return;
    }

    xTaskCreate(
        &ota_task_callback,
        "ota_task",
        8192,
        nullptr,
        5,
        &xOtaTaskHandler
    );
}

void ota_timer_callback(void *arg) {
    ESP_LOGI(TAG, "OTA timer triggered");
    run_ota_task();
}
