#ifndef SMARTAC_ESP32_CONTROLLER_MQTTMANAGER_H
#define SMARTAC_ESP32_CONTROLLER_MQTTMANAGER_H

#include "mqtt_client.h"
#include "freertos/FreeRTOS.h"
#include "esp_event.h"

class MqttManager {
public:
    static void start(const char *ssid, const char *pass, const char *broker, uint32_t port);

    static void publishState(const uint8_t *frame, size_t len);

private:
    static void wifiInit(const char *ssid, const char *pass);

    static void mqttInit(const char *broker, uint32_t port);

    static void mqttEventHandler(void *handler_args,
                                 esp_event_base_t base,
                                 int32_t event_id,
                                 void *event_data);

    static void wifiEventHandler(void *arg,
                                 esp_event_base_t event_base,
                                 int32_t event_id,
                                 void *event_data);

    static void ipEventHandler(void *arg,
                               esp_event_base_t event_base,
                               int32_t event_id,
                               void *event_data);

    static void handleCommandMessage(const uint8_t *data, size_t len);

    static inline esp_mqtt_client_handle_t client = nullptr;
    static inline bool connected = false;
    static inline bool wifiReady = false;
    static inline bool ipReady = false;
};

#endif
