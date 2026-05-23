package dev.cocot3ro.smartac.domain.model

import dev.cocot3ro.smartac.network.http.model.AcControlModel

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

fun AcControl.Power.toModel(): AcControlModel.Power = when (this) {
    AcControl.Power.POWER_ON -> AcControlModel.Power.POWER_ON
    AcControl.Power.POWER_OFF -> AcControlModel.Power.POWER_OFF
    AcControl.Power.TOGGLE -> AcControlModel.Power.TOGGLE
}

fun AcControl.FanSpeed.toModel(): AcControlModel.FanSpeed = when (this) {
    AcControl.FanSpeed.AUTO -> AcControlModel.FanSpeed.AUTO
    AcControl.FanSpeed.LOW -> AcControlModel.FanSpeed.LOW
    AcControl.FanSpeed.MEDIUM -> AcControlModel.FanSpeed.MEDIUM
    AcControl.FanSpeed.HIGH -> AcControlModel.FanSpeed.HIGH
}

fun AcControl.Mode.toModel(): AcControlModel.Mode = when (this) {
    AcControl.Mode.COOL -> AcControlModel.Mode.COOL
    AcControl.Mode.DRY -> AcControlModel.Mode.DRY
    AcControl.Mode.FAN -> AcControlModel.Mode.FAN
    AcControl.Mode.HEAT -> AcControlModel.Mode.HEAT
}

fun AcControl.Temperature.toModel(): AcControlModel.Temperature = when (this) {
    is AcControl.Temperature.Set -> AcControlModel.Temperature.Set(value = this.value)
    AcControl.Temperature.Increase -> AcControlModel.Temperature.Increase
    AcControl.Temperature.Decrease -> AcControlModel.Temperature.Decrease
}