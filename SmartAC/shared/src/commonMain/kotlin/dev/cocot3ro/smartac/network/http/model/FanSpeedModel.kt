package dev.cocot3ro.smartac.network.http.model

import kotlinx.serialization.Serializable

@Serializable
enum class FanSpeedModel {
    LOW,
    MEDIUM,
    HIGH,
    STOP;
}