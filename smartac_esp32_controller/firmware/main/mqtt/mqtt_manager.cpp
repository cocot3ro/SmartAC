#include "mqtt_manager.h"

#include <string>

#include "esp_crt_bundle.h"
#include "esp_log.h"
#include "ir_frame_t.h"

static auto TAG = "MQTT";
static constexpr auto AC_STATE_TOPIC_FMT = "smartac/%s/ac_state";
static constexpr auto CMD_TOPIC_FMT = "smartac/%s/cmd";
static constexpr auto CONNECTION_STATE_TOPIC_FMT = "smartac/%s/connection_state";
static constexpr auto ONLINE_STATE_MSG = "online";
static constexpr auto OFFLINE_STATE_MSG = "offline";

MqttManager::MqttManager(
    const QueueHandle_t *irCmdQueue,
    const QueueHandle_t *stateQueue,
    const char *broker,
    const uint32_t port,
    const char *mqtt_user,
    const char *mqtt_pass
) : irCmdQueue(*irCmdQueue), stateQueue(*stateQueue) {
    snprintf(ac_state_topic, sizeof(ac_state_topic), AC_STATE_TOPIC_FMT, mqtt_user);
    snprintf(cmd_topic, sizeof(cmd_topic), CMD_TOPIC_FMT, mqtt_user);
    snprintf(connection_state_topic, sizeof(connection_state_topic), CONNECTION_STATE_TOPIC_FMT, mqtt_user);

    esp_mqtt_client_config_t cfg = {};
    cfg.broker.address.transport = MQTT_TRANSPORT_OVER_SSL;
    cfg.broker.address.hostname = broker;
    cfg.broker.address.port = port;

    cfg.session.protocol_ver = MQTT_PROTOCOL_V_5;
    cfg.session.keepalive = 10;
    cfg.session.last_will.topic = connection_state_topic;
    cfg.session.last_will.msg = OFFLINE_STATE_MSG;
    cfg.session.last_will.msg_len = static_cast<int>(strlen(OFFLINE_STATE_MSG));
    cfg.session.last_will.qos = 1;
    cfg.session.last_will.retain = true;

    cfg.broker.verification.crt_bundle_attach = esp_crt_bundle_attach;
    cfg.credentials.username = mqtt_user;
    cfg.credentials.authentication.password = mqtt_pass;

    client = esp_mqtt_client_init(&cfg);
}

[[noreturn]]
void MqttManager::init() {
    ESP_ERROR_CHECK(esp_mqtt_client_register_event(
        client,
        MQTT_EVENT_ANY,
        &mqtt_event_handler,
        this
    ));

    ESP_ERROR_CHECK(esp_mqtt_client_start(client));

    while (true) {
        display_frame_t buffer{};
        if (xQueueReceive(stateQueue, &buffer, portMAX_DELAY) == pdTRUE) {
            publishState(buffer);
        }
    }
}

void MqttManager::mqtt_event_handler(void *handler_args, esp_event_base_t base, int32_t event_id, void *event_data) {
    const auto *event = static_cast<esp_mqtt_event_handle_t>(event_data);
    auto *self = static_cast<MqttManager *>(handler_args);

    switch (static_cast<esp_mqtt_event_id_t>(event_id)) {
        case MQTT_EVENT_BEFORE_CONNECT:
            ESP_LOGI(TAG, "Connecting to MQTT broker...");
            self->state = MqttConnectionState::CONNECTING;
            break;

        case MQTT_EVENT_CONNECTED:
            ESP_LOGI(TAG, "Connected to MQTT broker");

            esp_mqtt_client_subscribe(event->client, self->cmd_topic, 1);
            esp_mqtt_client_publish(event->client, self->connection_state_topic,
                                    ONLINE_STATE_MSG, 0, 1, true);

            self->state = MqttConnectionState::CONNECTED;

            break;

        case MQTT_EVENT_DISCONNECTED:
            ESP_LOGI(TAG, "Disconnected from MQTT broker");

            self->state = MqttConnectionState::DISCONNECTED;

            break;

        case MQTT_EVENT_DATA:
            ESP_LOGI(TAG, "MQTT RX");

            self->handleCommandMessage(
                event->data,
                event->data_len
            );
            break;

        case MQTT_EVENT_ERROR:
            ESP_LOGE("MQTT", "MQTT_EVENT_ERROR");
            if (event->error_handle) {
                ESP_LOGE("MQTT", "error_type=%d",
                         event->error_handle->error_type);
                ESP_LOGE("MQTT", "esp_tls_last_esp_err=0x%x",
                         event->error_handle->esp_tls_last_esp_err);
                ESP_LOGE("MQTT", "esp_tls_stack_err=0x%x",
                         event->error_handle->esp_tls_stack_err);
                ESP_LOGE("MQTT", "connect_return_code=%d",
                         event->error_handle->connect_return_code);
            }
            self->state = MqttConnectionState::DISCONNECTED;
            break;

        default:
            break;
    }
}

void MqttManager::publishState(const display_frame_t &frame) const {
    if (state != MqttConnectionState::CONNECTED) {
        ESP_LOGI(TAG, "MQTT client not connected, skipping publish");
        return;
    }

    esp_mqtt_client_publish(
        client,
        this->ac_state_topic,
        reinterpret_cast<const char *>(&frame),
        sizeof(frame),
        0,
        false
    );
}

// ReSharper disable once CppDFAUnreachableFunctionCall
void MqttManager::handleCommandMessage(
    const char *message,
    const uint32_t messageLen
) const {
    if (messageLen != IR_FRAME_SIZE) {
        ESP_LOGW(TAG, "Received command message with invalid length: %d", messageLen);
        return;
    }

    auto *frame = static_cast<ir_frame_t *>(malloc(IR_FRAME_SIZE));
    if (!frame) {
        ESP_LOGE(TAG, "Failed to allocate memory for command frame");
        return;
    }

    memcpy(frame, message, IR_FRAME_SIZE);

    if (xQueueSend(irCmdQueue, &frame, 0) != pdTRUE) {
        ESP_LOGW(TAG, "Failed to enqueue command frame");
        free(frame);
    }
}
