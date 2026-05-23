package dev.cocot3ro.smartac.core.display

import dev.cocot3ro.smartac.core.state.AcMode

enum class DisplayMode(val value: Int) {
    COOL(0x02),
    DRY(0x03),
    FAN(0x00),
    HEAT(0x01);

    companion object {
        fun fromCode(value: Int): DisplayMode? = entries.firstOrNull { it.value == value }
    }
}

fun DisplayMode.toAcMode(): AcMode = when (this) {
    DisplayMode.COOL -> AcMode.COOL
    DisplayMode.DRY -> AcMode.DRY
    DisplayMode.FAN -> AcMode.FAN
    DisplayMode.HEAT -> AcMode.HEAT
}