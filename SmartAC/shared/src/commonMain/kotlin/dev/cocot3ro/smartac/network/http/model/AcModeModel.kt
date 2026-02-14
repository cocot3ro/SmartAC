package dev.cocot3ro.smartac.network.http.model

import kotlinx.serialization.Serializable

@Serializable
enum class AcModeModel {
    COOL,
    DRY,
    FAN,
    HEAT;
}