#ifndef FIRMWARE_DISPLAY_READER_H
#define FIRMWARE_DISPLAY_READER_H

#include "driver/uart.h"
#include "driver/gpio.h"

class DisplayReader {
public:
    DisplayReader(uart_port_t uart_port, gpio_num_t rx_pin, int baud_rate, QueueHandle_t *frameQueue);

    [[noreturn]] void read_loop() const;

private:
    uart_port_t uart_port;
    QueueHandle_t *frameQueue;
};

#endif //FIRMWARE_DISPLAY_READER_H
