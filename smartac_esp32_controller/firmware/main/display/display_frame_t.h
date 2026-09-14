#ifndef FIRMWARE_DISPLAY_FRAME_H
#define FIRMWARE_DISPLAY_FRAME_H

#include <cstdint>

#pragma pack(push, 1)
struct display_frame_t {
    uint8_t address;

    uint8_t super: 1;
    uint8_t sleep: 1;
    uint8_t : 2;
    uint8_t i_feel: 1;
    uint8_t : 2;
    uint8_t ac_power: 1;

    uint8_t : 8;

    uint8_t : 3;
    uint8_t ac_running: 1;
    uint8_t : 1;
    uint8_t display_power: 1;
    uint8_t : 2;

    uint8_t fan_speed: 2;
    uint8_t mode: 2;
    uint8_t smart: 1;
    uint8_t : 2;
    uint8_t temp_display: 1;

    uint8_t : 8;

    uint8_t : 4;
    uint8_t fan_auto: 1;
    uint8_t : 1;
    uint8_t error: 1;
    uint8_t : 1;

    uint8_t : 1;
    uint8_t target_temp: 7;

    uint8_t : 1;
    uint8_t current_temp: 7;

    uint8_t : 8;

    uint8_t error_code;

    uint8_t : 6;
    uint8_t timer_off_enable: 1;
    uint8_t timer_on_enable: 1;

    uint8_t : 8;

    uint8_t : 8;

    uint8_t : 8;

    uint8_t : 8;

    uint8_t : 8;

    uint8_t : 8;

    uint8_t : 8;

    uint8_t : 8;

    uint8_t : 8;

    uint8_t : 8;

    uint8_t : 8;

    uint8_t : 8;

    uint8_t checksum;
};
#pragma pack(pop)

static constexpr auto DISPLAY_FRAME_SIZE = sizeof(display_frame_t);
constexpr uint8_t DISPLAY_FRAME_ADDRESS = 0xAA;

#endif //FIRMWARE_DISPLAY_FRAME_H
