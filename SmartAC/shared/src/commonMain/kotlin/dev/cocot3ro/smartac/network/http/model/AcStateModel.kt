package dev.cocot3ro.smartac.network.http.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AcStateModel(
    val power: Boolean,

    @SerialName(value = "is_fan_auto")
    val isFanAuto: Boolean,

    @SerialName(value = "fan_speed")
    val fanSpeed: FanSpeedModel,

    val mode: AcModeModel,

    @SerialName(value = "current_temp")
    val currentTemp: Int,

    @SerialName(value = "target_temp")
    val targetTemp: Int,

    @SerialName(value = "error_code")
    val errorCode: UByte?
) : WebSocketMessage {
    override val type: String = "state"
}