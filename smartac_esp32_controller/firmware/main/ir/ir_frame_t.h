#ifndef FIRMWARE_IR_FRAME_H
#define FIRMWARE_IR_FRAME_H

#include <cstdint>

#pragma pack(push, 1)
struct ir_frame_t {
    uint16_t address;

    uint8_t fan_speed: 2;
    uint8_t power_toggle: 1;
    uint8_t dry_temp_1: 2;
    uint8_t dry_sign: 1;
    uint8_t swing_1: 1;

    uint8_t mode: 3;
    uint8_t : 1;
    uint8_t temp: 4;

    uint8_t : 8;

    uint8_t : 4;
    uint8_t super_1: 1;
    uint8_t : 2;
    uint8_t super_2: 1;

    uint8_t clock_hour: 4;
    uint8_t dimmer_toggle: 1;
    uint8_t temp_unit: 1;
    uint8_t button_pressed: 1;

    uint8_t clock_minute: 6;
    uint8_t : 1;
    uint8_t timer_off_enable: 1;

    uint8_t timer_off_hour: 5;
    uint8_t : 1;
    uint8_t swing_2: 1;
    uint8_t : 1;

    uint8_t timer_off_minute: 6;
    uint8_t : 1;
    uint8_t timer_on_enable: 1;

    uint8_t timer_on_hour: 5;
    uint8_t : 3;

    uint8_t timer_on_minute: 6;
    uint8_t : 1;
    uint8_t i_feel: 1;

    uint8_t i_feel_temp: 4;
    uint8_t : 4;

    uint8_t checksum1;

    uint8_t : 8;

    uint8_t command;

    uint8_t : 2;
    uint8_t dry_temp_2: 3;
    uint8_t : 3;

    uint8_t : 7;
    uint8_t fahrenheit_plus_one: 1;

    uint8_t remote_model;

    uint8_t : 8;

    uint8_t checksum2;
};
#pragma pack(pop)

static constexpr auto IR_FRAME_SIZE = sizeof(ir_frame_t);

#endif //FIRMWARE_IR_FRAME_H
