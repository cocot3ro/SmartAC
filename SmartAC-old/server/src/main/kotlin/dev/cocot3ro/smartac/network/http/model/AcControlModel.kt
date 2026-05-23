package dev.cocot3ro.smartac.network.http.model

import dev.cocot3ro.smartac.core.control.AcControl

fun AcControlModel.Mode.toAcControl(): AcControl.Mode = when (this) {
    AcControlModel.Mode.COOL -> AcControl.Mode.COOL
    AcControlModel.Mode.DRY -> AcControl.Mode.DRY
    AcControlModel.Mode.FAN -> AcControl.Mode.FAN
    AcControlModel.Mode.HEAT -> AcControl.Mode.HEAT
}

fun AcControlModel.Power.toAcControl(): AcControl.Power = when (this) {
    AcControlModel.Power.POWER_ON -> AcControl.Power.POWER_ON
    AcControlModel.Power.POWER_OFF -> AcControl.Power.POWER_OFF
    AcControlModel.Power.TOGGLE -> AcControl.Power.TOGGLE
}

fun AcControlModel.FanSpeed.toAcControl(): AcControl.FanSpeed = when (this) {
    AcControlModel.FanSpeed.AUTO -> AcControl.FanSpeed.AUTO
    AcControlModel.FanSpeed.LOW -> AcControl.FanSpeed.LOW
    AcControlModel.FanSpeed.MEDIUM -> AcControl.FanSpeed.MEDIUM
    AcControlModel.FanSpeed.HIGH -> AcControl.FanSpeed.HIGH
}

fun AcControlModel.Temperature.toAcControl(): AcControl.Temperature = when (this) {
    is AcControlModel.Temperature.Set -> AcControl.Temperature.Set(this.value)
    AcControlModel.Temperature.Increase -> AcControl.Temperature.Increase
    AcControlModel.Temperature.Decrease -> AcControl.Temperature.Decrease
}