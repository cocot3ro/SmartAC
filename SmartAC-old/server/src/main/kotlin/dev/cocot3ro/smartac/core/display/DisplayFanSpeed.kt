package dev.cocot3ro.smartac.core.display

import dev.cocot3ro.smartac.core.state.AcFanSpeed

enum class DisplayFanSpeed(private val value: Int) {
    STOP(0x00),
    LOW(0x02),
    MEDIUM(0x03),
    HIGH(0x01);

    companion object {
        fun fromCode(value: Int): DisplayFanSpeed? = entries.firstOrNull { it.value == value }
    }
}

fun DisplayFanSpeed.toAcFanSpeed(): AcFanSpeed = when (this) {
    DisplayFanSpeed.LOW -> AcFanSpeed.LOW
    DisplayFanSpeed.MEDIUM -> AcFanSpeed.MEDIUM
    DisplayFanSpeed.HIGH -> AcFanSpeed.HIGH
    DisplayFanSpeed.STOP -> AcFanSpeed.STOP
}
