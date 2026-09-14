#include "ir_emitter.h"

#include <string>
#include <cmath>
#include "esp_timer.h"

constexpr uint16_t HEADER_MARK = 9000;
constexpr uint16_t HEADER_SPACE = 4500;
constexpr uint16_t BIT_MARK = 615;
constexpr uint16_t ZERO_SPACE = 530;
constexpr uint16_t ONE_SPACE = 1640;
constexpr uint16_t PAUSE_SPACE = 7940;

IrEmitter::IrEmitter(
    const gpio_num_t pin,
    const uint32_t carrier_freq_hz
) : ir_gpio(pin), carrier_freq_hz(carrier_freq_hz) {
    // Calcular medio periodo
    // periodo = 1/f → [s]  → *1e6 para us
    const float period_us = 1'000'000.0f / static_cast<float>(carrier_freq_hz);
    half_period_us = static_cast<uint32_t>(std::round(period_us / 2));

    gpio_config_t cfg = {};
    cfg.pin_bit_mask = 1ULL << ir_gpio;
    cfg.mode = GPIO_MODE_OUTPUT;
    cfg.pull_up_en = GPIO_PULLUP_DISABLE;
    cfg.pull_down_en = GPIO_PULLDOWN_DISABLE;
    cfg.intr_type = GPIO_INTR_DISABLE;
    gpio_config(&cfg);

    gpio_set_level(ir_gpio, 0);
}

void IrEmitter::send_ir_frame(ir_frame_t *frame) const {
    const auto data = reinterpret_cast<uint8_t *>(frame);

    // ---------------------------
    // HEADER
    // ---------------------------
    irMark(HEADER_MARK);
    irSpace(HEADER_SPACE);

    // ---------------------------
    // PRIMER BLOQUE: 6 bytes
    // ---------------------------
    for (int i = 0; i < 6; i++)
        sendByte(data[i]);

    // ---------------------------
    // PAUSA ENTRE BLOQUES
    // ---------------------------
    irMark(BIT_MARK);
    irSpace(PAUSE_SPACE);

    // ---------------------------
    // SEGUNDO BLOQUE: 8 bytes
    // ---------------------------
    for (int i = 6; i < 14; i++)
        sendByte(data[i]);

    // ---------------------------
    // PAUSA ENTRE BLOQUES
    // ---------------------------
    irMark(BIT_MARK);
    irSpace(PAUSE_SPACE);

    // ---------------------------
    // TERCER BLOQUE: 7 bytes
    // ---------------------------
    for (int i = 14; i < 21; i++)
        sendByte(data[i]);

    // ---------------------------
    // FINAL MARK
    // ---------------------------
    irMark(BIT_MARK);
}

void IrEmitter::sendByte(const uint8_t byte) const {
    for (int i = 0; i < 8; i++) {
        irMark(BIT_MARK);

        if (byte & (1 << i)) {
            irSpace(ONE_SPACE);
        } else {
            irSpace(ZERO_SPACE);
        }
    }
}

void IrEmitter::irMark(const uint32_t duration_us) const {
    const uint64_t start = esp_timer_get_time();
    while ((esp_timer_get_time() - start) < duration_us) {
        gpio_set_level(ir_gpio, 1);
        esp_rom_delay_us(half_period_us);
        gpio_set_level(ir_gpio, 0);
        esp_rom_delay_us(half_period_us);
    }
}

void IrEmitter::irSpace(const uint32_t duration_us) const {
    gpio_set_level(ir_gpio, 0);
    esp_rom_delay_us(duration_us);
}
