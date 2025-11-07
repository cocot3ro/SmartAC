package dev.cocot3ro.smartac.network.http.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AcStatusModel(
    val power: Boolean,

    val mode: String,

    val temp: Byte,

    @SerialName("target_temp")
    val targetTemp: Byte,

    @SerialName("fan_speed")
    val fanSpeed: Byte,

    val swing: Boolean
)
