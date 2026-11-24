package dev.cocot3ro.smartac.data.network.model

import io.github.z4kn4fein.semver.Version
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VersionCheckResponse(
    val current: Version,

    val latest: Version,

    @SerialName("update_available")
    val updateAvailable: Boolean
)