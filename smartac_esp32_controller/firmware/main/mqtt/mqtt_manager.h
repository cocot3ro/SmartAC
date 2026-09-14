#ifndef FIRMWARE_MQTT_MANAGER_H
#define FIRMWARE_MQTT_MANAGER_H

#include <atomic>

#include "mqtt_client.h"
#include "freertos/queue.h"

#include "display_frame_t.h"
#include "mqtt_connection_state.h"

static constexpr size_t MAX_DEVICE_CODE_LEN = 36;

class MqttManager {
public:
    MqttManager(
        const QueueHandle_t *irCmdQueue,
        const QueueHandle_t *stateQueue,
        const char *broker,
        uint32_t port,
        const char *mqtt_user,
        const char *mqtt_pass
    );

    [[noreturn]]
    void init();

private:
    esp_mqtt5_client_handle_t client = nullptr;
    std::atomic<MqttConnectionState> state = MqttConnectionState::DISCONNECTED;

    char ac_state_topic[sizeof("smartac//ac_state") + MAX_DEVICE_CODE_LEN]{};
    char cmd_topic[sizeof("smartac//cmd") + MAX_DEVICE_CODE_LEN]{};
    char connection_state_topic[sizeof("smartac//connection_state") + MAX_DEVICE_CODE_LEN]{};

    QueueHandle_t irCmdQueue;
    QueueHandle_t stateQueue;

    static void mqtt_event_handler(void *handler_args,
                              esp_event_base_t base,
                              int32_t event_id,
                              void *event_data);

    void publishState(const display_frame_t &frame) const;

    void handleCommandMessage(
        const char *message,
        uint32_t messageLen
    ) const;
};

#endif //FIRMWARE_MQTT_MANAGER_H
