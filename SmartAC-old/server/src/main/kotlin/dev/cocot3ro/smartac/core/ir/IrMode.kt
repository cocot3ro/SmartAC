package dev.cocot3ro.smartac.core.ir

enum class IrMode(val code: UByte) {
    HEAT(0x00U),
    COOL(0x02U),
    DRY(0x03U),
    FAN(0x04U)
}