package dev.cocot3ro.smartac.domain.model

import dev.cocot3ro.smartac.data.network.model.HealthResponse
import io.github.z4kn4fein.semver.Version

data class HealthDTO(
    val isSetup: Boolean,
    val version: Version
)

fun HealthResponse.toDto(): HealthDTO = HealthDTO(
    isSetup = this.setup,
    version = this.version
)