#include <cstring>
#include "freertos/FreeRTOS.h"
#include "freertos/task.h"
#include "esp_log.h"

#include "DisplayReader.h"
#include "IrSender.h"
#include "MqttManager.h"
#include "secrets.h"

IrSender irSender(GPIO_NUM_5, 38000);
DisplayReader displayReader(UART_NUM_2, GPIO_NUM_4, 1200);

QueueHandle_t displayFrameQueue = nullptr;

[[noreturn]] void displayReaderTask(void *pv) {
    displayReader.readLoop(displayFrameQueue);
}

void irSendTask(void *pv) {
    const uint8_t *frame = static_cast<uint8_t *>(pv);
    irSender.sendIr(frame);
    free((void *) frame);
    vTaskDelete(nullptr);
}

[[noreturn]] void publishTask(void *pv) {
    uint8_t frame[DISPLAY_FRAME_SIZE];

    while (true) {
        if (xQueueReceive(displayFrameQueue, &frame, portMAX_DELAY) == pdTRUE) {
            MqttManager::publishState(frame, DISPLAY_FRAME_SIZE);
        }
    }
}

extern "C" void app_main(void) {
    displayFrameQueue = xQueueCreate(5, DISPLAY_FRAME_SIZE);

    // WiFi + MQTT (core 0)
    MqttManager::start(WIFI_SSID, WIFI_PASS, MQTT_BROKER, MQTT_PORT);

    xTaskCreatePinnedToCore(displayReaderTask, "displayReaderTask", 4096, nullptr, 5, nullptr, 1);
    xTaskCreatePinnedToCore(publishTask, "publishTask", 4096, nullptr, 5, nullptr, 0);
}
