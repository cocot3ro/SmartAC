package dev.cocot3ro.smartac.core.ir

enum class IrFanSpeed(val code: UByte) {
    AUTO(0x00U),
    HIGH(0x01U),
    MEDIUM(0x02U),
    LOW(0x03U)
}