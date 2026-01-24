package dev.cocot3ro.smartac.domain.model

data class AcState(
    val power: Boolean,
    val isFanAuto: Boolean,
    val fanSpeed: FanSpeed,
    val mode: AcMode,
    val currentTemp: Int,
    val targetTemp: Int,
    val errorCode: UByte?
)
