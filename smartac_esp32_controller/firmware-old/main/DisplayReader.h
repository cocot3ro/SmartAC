#ifndef SMARTAC_ESP32_CONTROLLER_DISPLAYREADER_H
#define SMARTAC_ESP32_CONTROLLER_DISPLAYREADER_H

#include "driver/uart.h"
#include "driver/gpio.h"

constexpr uint8_t DISPLAY_FRAME_SIZE = 25;
constexpr uint8_t DISPLAY_FRAME_ADDRESS = 0xAA;

class DisplayReader {
public:
    DisplayReader(uart_port_t uart_port, gpio_num_t rx_pin, int baud_rate);

    [[noreturn]] void readLoop(QueueHandle_t frameQueue) const;

private:
    uart_port_t uart_port;
};

#endif //SMARTAC_ESP32_CONTROLLER_DISPLAYREADER_H
