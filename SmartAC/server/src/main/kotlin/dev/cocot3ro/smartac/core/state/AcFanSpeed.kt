package dev.cocot3ro.smartac.core.state

import dev.cocot3ro.smartac.core.ir.IrFanSpeed
import dev.cocot3ro.smartac.network.http.model.FanSpeedModel

enum class AcFanSpeed {
    LOW,
    MEDIUM,
    HIGH,
    STOP;
}

fun AcFanSpeed.toModel(): FanSpeedModel = when (this) {
    AcFanSpeed.LOW -> FanSpeedModel.LOW
    AcFanSpeed.MEDIUM -> FanSpeedModel.MEDIUM
    AcFanSpeed.HIGH -> FanSpeedModel.HIGH
    AcFanSpeed.STOP -> FanSpeedModel.STOP
}

fun AcFanSpeed.toIrFanSpeed(): IrFanSpeed = when (this) {
    AcFanSpeed.LOW -> IrFanSpeed.LOW
    AcFanSpeed.MEDIUM -> IrFanSpeed.MEDIUM
    AcFanSpeed.HIGH -> IrFanSpeed.HIGH
    AcFanSpeed.STOP -> IrFanSpeed.AUTO
}