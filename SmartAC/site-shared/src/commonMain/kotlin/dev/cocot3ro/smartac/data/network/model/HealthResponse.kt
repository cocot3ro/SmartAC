package dev.cocot3ro.smartac.data.network.model

import io.github.z4kn4fein.semver.Version
import kotlinx.serialization.Serializable

@Serializable
data class HealthResponse(
    val version: Version,
    val setup: Boolean
)