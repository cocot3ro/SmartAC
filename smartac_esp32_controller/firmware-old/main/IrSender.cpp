#include "IrSender.h"
#include <cmath>

#include "esp_rom_sys.h"

IrSender::IrSender(const gpio_num_t gpio, const uint32_t carrier_freq_hz)
    : ir_gpio(gpio), carrier_freq(carrier_freq_hz) {
    // Calcular medio periodo
    // periodo = 1/f → [s]  → *1e6 para us
    const float period_us = 1'000'000.0f / static_cast<float>(carrier_freq);
    half_period_us = static_cast<uint32_t>(std::round(period_us / 2));

    half_period_us = 1000000UL / carrier_freq_hz / 2; // HIGH/LOW cada medio periodo

    gpio_config_t cfg = {};
    cfg.pin_bit_mask = 1ULL << ir_gpio;
    cfg.mode = GPIO_MODE_OUTPUT;
    cfg.pull_up_en = GPIO_PULLUP_DISABLE;
    cfg.pull_down_en = GPIO_PULLDOWN_DISABLE;
    cfg.intr_type = GPIO_INTR_DISABLE;
    gpio_config(&cfg);

    gpio_set_level(ir_gpio, 0);
}

void IrSender::sendIr(const uint8_t *data) const {
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
    irMark(BITMARK);
    irSpace(PAUSE_SPACE);

    // ---------------------------
    // SEGUNDO BLOQUE: 8 bytes
    // ---------------------------
    for (int i = 6; i < 14; i++)
        sendByte(data[i]);

    // ---------------------------
    // PAUSA ENTRE BLOQUES
    // ---------------------------
    irMark(BITMARK);
    irSpace(PAUSE_SPACE);

    // ---------------------------
    // TERCER BLOQUE: 7 bytes
    // ---------------------------
    for (int i = 14; i < 21; i++)
        sendByte(data[i]);

    // ---------------------------
    // FINAL MARK
    // ---------------------------
    irMark(BITMARK);
}

void IrSender::sendByte(const uint8_t byte) const {
    for (int i = 0; i < 8; i++) {
        irMark(BITMARK);

        if (byte & (1 << i)) {
            irSpace(ONE_SPACE);
        } else {
            irSpace(ZERO_SPACE);
        }
    }
}

void IrSender::irMark(const uint32_t duration_us) const {
    const uint64_t start = esp_timer_get_time();
    while ((esp_timer_get_time() - start) < duration_us) {
        gpio_set_level(ir_gpio, 1);
        esp_rom_delay_us(half_period_us);
        gpio_set_level(ir_gpio, 0);
        esp_rom_delay_us(half_period_us);
    }
}

void IrSender::irSpace(const uint32_t duration_us) const {
    gpio_set_level(ir_gpio, 0);
    esp_rom_delay_us(duration_us);
}
