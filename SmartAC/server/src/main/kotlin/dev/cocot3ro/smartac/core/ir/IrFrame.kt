package dev.cocot3ro.smartac.core.ir

import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

enum class Commands(val code: UByte) {
    POWER(0x01U),
    TEMPERATURE(0x02U),
    MODE(0x06U),
    FAN_SPEED(0x11U);
}

private const val LENGTH = 21
private const val ADDRESS_1: UByte = 0x83U
private const val ADDRESS_2: UByte = 0x06U
private const val REMOTE_MODEL: UByte = 0x08U
private const val POWER_MASK: UByte = 0x20U
private const val BUTTON_MASK: UByte = 0x80U
private const val TEMP_OFFSET: Int = 16
const val TEMP_MIN = 16
const val TEMP_MAX = 30

@OptIn(ExperimentalUnsignedTypes::class, ExperimentalTime::class)
fun createIrFrame(
    powerToggle: Boolean,
    mode: IrMode,
    temp: Int,
    fanSpeed: IrFanSpeed,
    cmd: Commands,
    clockData: LocalTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time
): UByteArray {
    return UByteArray(LENGTH).apply {
        this[0] = ADDRESS_1
        this[1] = ADDRESS_2

        this[2] = fanSpeed.code.reverseByte()
        if (powerToggle) this[2] = this[2] or POWER_MASK
        this[2] = this[2].reverseByte()

        this[3] = mode.code.reverseByte()
        this[3] = this[3] or ((temp.coerceIn(TEMP_MIN, TEMP_MAX)) - TEMP_OFFSET shl 4)
            .toUByte().reverseByte()
        this[3] = this[3].reverseByte()

        this[6] = (clockData.hour.toUByte().reverseByte().toUInt() shr 3).toUByte()
        this[6] = this[6] or BUTTON_MASK

        this[7] = (clockData.minute.toUByte().reverseByte().toUInt() shr 2).toUByte()

        this[13] = this.take(13).drop(2).reduce { acc: UByte, b: UByte -> acc xor b }

        this[15] = cmd.code

        this[18] = REMOTE_MODEL

        this[20] = this.takeLast(7).dropLast(1).reduce { acc: UByte, b: UByte -> acc xor b }
    }
}

private fun UByte.reverseByte(): UByte {
    var x: UInt = this.toUInt()
    x = ((x and 0x55U) shl 1) or ((x and 0xAAU) shr 1)
    x = ((x and 0x33U) shl 2) or ((x and 0xCCU) shr 2)
    x = ((x and 0x0FU) shl 4) or ((x and 0xF0U) shr 4)
    return x.toUByte()
}
