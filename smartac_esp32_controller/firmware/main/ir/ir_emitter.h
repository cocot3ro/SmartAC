#ifndef FIRMWARE_IR_SENDER_H
#define FIRMWARE_IR_SENDER_H

#include "driver/gpio.h"
#include "ir_frame_t.h"
#include "esp_task.h"

class IrEmitter {
public:
    IrEmitter(gpio_num_t pin, uint32_t carrier_freq_hz);

    void send_ir_frame(ir_frame_t *frame) const;

private:
    gpio_num_t ir_gpio;
    uint32_t carrier_freq_hz;
    uint32_t half_period_us;

    void sendByte(uint8_t byte) const;

    void irMark(uint32_t duration_us) const;

    void irSpace(uint32_t duration_us) const;
};

#endif //FIRMWARE_IR_SENDER_H
