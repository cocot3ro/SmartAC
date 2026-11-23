package dev.cocot3ro.smartac.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VersionCheckResponse(
    val current: Version,

    val latest: Version,

    @SerialName("update_available")
    val updateAvailable: Boolean
)