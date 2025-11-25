#include "DisplayReader.h"

DisplayReader::DisplayReader(
    const uart_port_t uart_port, const gpio_num_t rx_pin, const int baud_rate
) : uart_port(uart_port) {
    uart_config_t uart_config = {};
    uart_config.baud_rate = baud_rate;
    uart_config.data_bits = UART_DATA_8_BITS;
    uart_config.parity = UART_PARITY_DISABLE;
    uart_config.stop_bits = UART_STOP_BITS_1;
    uart_config.flow_ctrl = UART_HW_FLOWCTRL_DISABLE;
    uart_config.source_clk = UART_SCLK_APB;

    uart_param_config(uart_port, &uart_config);

    uart_set_pin(
        uart_port,
        UART_PIN_NO_CHANGE,
        rx_pin,
        UART_PIN_NO_CHANGE,
        UART_PIN_NO_CHANGE
    );

    uart_driver_install(
        uart_port,
        256,
        0,
        0,
        nullptr,
        0
    );
}

[[noreturn]] void DisplayReader::readLoop(QueueHandle_t frameQueue) const {
    uint8_t buffer[DISPLAY_FRAME_SIZE];
    size_t idx = 0;

    while (true) {
        uint8_t byte;

        if (uart_read_bytes(uart_port, &byte, 1, pdMS_TO_TICKS(20)) != 1)
            continue;

        if (idx == 0 && byte != DISPLAY_FRAME_ADDRESS)
            continue;

        buffer[idx++] = byte;

        if (idx == DISPLAY_FRAME_SIZE) {
            xQueueSend(frameQueue, &buffer, 0);

            idx = 0;
        }
    }
}
