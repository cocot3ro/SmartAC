package dev.cocot3ro.smartac.core.state

import dev.cocot3ro.smartac.core.ir.IrFanSpeed
import dev.cocot3ro.smartac.core.ir.IrMode
import dev.cocot3ro.smartac.network.http.model.AcModeModel

enum class AcMode(val defaultTemp: Int, val defaultFanSpeed: IrFanSpeed) {
    COOL(24, IrFanSpeed.AUTO),
    DRY(0, IrFanSpeed.AUTO),
    FAN(0, IrFanSpeed.HIGH),
    HEAT(21, IrFanSpeed.AUTO);
}

fun AcMode.toModel(): AcModeModel = when (this) {
    AcMode.COOL -> AcModeModel.COOL
    AcMode.DRY -> AcModeModel.DRY
    AcMode.FAN -> AcModeModel.FAN
    AcMode.HEAT -> AcModeModel.HEAT
}

fun AcMode.toIrMode(): IrMode = when (this) {
    AcMode.HEAT -> IrMode.HEAT
    AcMode.COOL -> IrMode.COOL
    AcMode.DRY -> IrMode.DRY
    AcMode.FAN -> IrMode.FAN
}