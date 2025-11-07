#include <string>

#include "freertos/FreeRTOS.h"
#include "freertos/task.h"
#include "esp_log.h"

[[noreturn]] void taskA(void *pv) {
    while (true) {
        ESP_LOGI("TaskA", "Ejecutando en core %d", xPortGetCoreID());
        vTaskDelay(pdMS_TO_TICKS(1000));
    }
}

[[noreturn]] void taskB(void *pv) {
    while (true) {
        ESP_LOGI("TaskB", "Ejecutando en core %d", xPortGetCoreID());
        vTaskDelay(pdMS_TO_TICKS(1500));
    }
}

extern "C" void app_main(void) {
    // una por core
    xTaskCreatePinnedToCore(taskA, "TaskA", 2048, nullptr, 5, nullptr, 0);
    xTaskCreatePinnedToCore(taskB, "TaskB", 2048, nullptr, 5, nullptr, 1);
}
