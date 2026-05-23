package dev.cocot3ro.smartac.core.state

import dev.cocot3ro.smartac.network.http.model.AcStateModel

data class AcState(
    val power: Boolean,
    val mode: AcMode,
    val currentTemp: Int,
    val targetTemp: Int,
    val isFanAuto: Boolean,
    val isFanStop: Boolean,
    val fanSpeed: AcFanSpeed,
    val errorCode: UByte?
)

fun AcState.toModel(): AcStateModel = AcStateModel(
    power = power,
    mode = mode.toModel(),
    currentTemp = currentTemp,
    targetTemp = targetTemp,
    isFanAuto = isFanAuto,
    fanSpeed = fanSpeed.toModel(),
    errorCode = errorCode
)
