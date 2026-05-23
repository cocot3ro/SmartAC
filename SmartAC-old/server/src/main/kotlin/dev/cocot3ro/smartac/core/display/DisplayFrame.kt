package dev.cocot3ro.smartac.core.display

import dev.cocot3ro.smartac.core.state.ChecksumMismatchException
import dev.cocot3ro.smartac.core.state.InvalidAddressException
import dev.cocot3ro.smartac.core.state.InvalidLengthException

private object Indexes {
    const val POWER = 1
    const val FAN_AUTO = 6
    const val FAN_SPEED = 4
    const val MODE = 4
    const val CURRENT_TEMP = 8
    const val TARGET_TEMP = 7
    const val ERROR = 6
    const val ERROR_CODE = 10
}

private object Masks {
    const val POWER = 0x80
    const val FAN_AUTO = 0x10
    const val FAN_SPEED = 0x03
    const val MODE = 0x0C
    const val TEMP = 0xFE
    const val ERROR = 0x40
}

private const val LENGTH = 25
private const val ADDRESS = 0xAA
private const val TEMP_OFFSET = 0x14

data class DisplayFrame(
    val power: Boolean,
    val isFanAuto: Boolean,
    val fanSpeed: DisplayFanSpeed,
    val mode: DisplayMode,
    val currentTemp: Int,
    val targetTemp: Int,
    val errorCode: UByte?
) {

    companion object {
        @OptIn(ExperimentalUnsignedTypes::class)
        fun decode(raw: UByteArray): Result<DisplayFrame> {
            if (raw.size != LENGTH)
                return Result.failure(InvalidLengthException())

            if (raw.first().toInt() != ADDRESS)
                return Result.failure(InvalidAddressException())

            if (calculateChecksum(raw.dropLast(1)) != raw.last())
                return Result.failure(ChecksumMismatchException())

            operator fun UByteArray.get(index: Int, mask: Int): Int = this[index].toInt() and mask

            val power: Boolean = raw[Indexes.POWER, Masks.POWER] ushr 7 != 0
            val isFanAuto: Boolean = raw[Indexes.FAN_AUTO, Masks.FAN_AUTO] ushr 4 != 0
            val fanSpeed: DisplayFanSpeed =
                DisplayFanSpeed.fromCode(raw[Indexes.FAN_SPEED, Masks.FAN_SPEED])!!
            val mode: DisplayMode = DisplayMode.fromCode(raw[Indexes.MODE, Masks.MODE] ushr 2)!!
            val currentTemp: Int = (raw[Indexes.CURRENT_TEMP, Masks.TEMP] ushr 1) - TEMP_OFFSET
            val targetTemp: Int = (raw[Indexes.TARGET_TEMP, Masks.TEMP] ushr 1) - TEMP_OFFSET
            val errorCode: UByte? = raw[Indexes.ERROR_CODE].takeIf { _ ->
                (raw[Indexes.ERROR, Masks.ERROR] ushr 6) != 0
            }

            return Result.success(
                DisplayFrame(
                    power = power,
                    isFanAuto = isFanAuto,
                    fanSpeed = fanSpeed,
                    mode = mode,
                    currentTemp = currentTemp,
                    targetTemp = targetTemp,
                    errorCode = errorCode
                )
            )
        }

        @OptIn(ExperimentalUnsignedTypes::class)
        private fun calculateChecksum(raw: List<UByte>): UByte {
            return (0xFF - (raw.sumOf(UByte::toInt) and 0xFF)).toUByte()
        }
    }
}
