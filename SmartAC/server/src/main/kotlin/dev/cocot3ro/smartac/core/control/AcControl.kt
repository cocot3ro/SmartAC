package dev.cocot3ro.smartac.core.control

import dev.cocot3ro.smartac.core.ir.IrFanSpeed
import dev.cocot3ro.smartac.core.state.AcFanSpeed
import dev.cocot3ro.smartac.core.state.AcMode

class AcControl {

    enum class Power {
        POWER_ON,
        POWER_OFF,
        TOGGLE;
    }

    enum class Mode {
        COOL,
        DRY,
        FAN,
        HEAT;
    }

    enum class FanSpeed {
        AUTO,
        LOW,
        MEDIUM,
        HIGH;
    }

    sealed class Temperature {
        data class Set(val value: Int) : Temperature()
        data object Increase : Temperature()
        data object Decrease : Temperature()
    }
}

fun AcControl.Mode.toAcMode(): AcMode = when (this) {
    AcControl.Mode.COOL -> AcMode.COOL
    AcControl.Mode.DRY -> AcMode.DRY
    AcControl.Mode.FAN -> AcMode.FAN
    AcControl.Mode.HEAT -> AcMode.HEAT
}

fun AcControl.FanSpeed.toIrFanSpeed(): IrFanSpeed = when (this) {
    AcControl.FanSpeed.AUTO -> IrFanSpeed.AUTO
    AcControl.FanSpeed.LOW -> IrFanSpeed.LOW
    AcControl.FanSpeed.MEDIUM -> IrFanSpeed.MEDIUM
    AcControl.FanSpeed.HIGH -> IrFanSpeed.HIGH
}
