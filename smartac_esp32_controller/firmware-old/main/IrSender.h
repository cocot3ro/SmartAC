#ifndef SMARTAC_ESP32_CONTROLLER_IRSENDER_H
#define SMARTAC_ESP32_CONTROLLER_IRSENDER_H

#include "driver/gpio.h"
#include "esp_timer.h"

constexpr uint8_t IR_DATA_SIZE = 21;

constexpr uint16_t HEADER_MARK = 9000;
constexpr uint16_t HEADER_SPACE = 4500;
constexpr uint16_t BITMARK = 615;
constexpr uint16_t ZERO_SPACE = 530;
constexpr uint16_t ONE_SPACE = 1640;
constexpr uint16_t PAUSE_SPACE = 7940;

class IrSender {
public:
    IrSender(gpio_num_t gpio, uint32_t carrier_freq_hz);

    void sendIr(const uint8_t *data) const;

private:
    gpio_num_t ir_gpio; // pin IR LED
    uint32_t carrier_freq; // en Hz
    uint32_t half_period_us; // periodo/2 en microsegundos

    void sendByte(uint8_t byte) const;

    void irMark(uint32_t duration_us) const;

    void irSpace(uint32_t duration_us) const;
};

#endif //SMARTAC_ESP32_CONTROLLER_IRSENDER_H
