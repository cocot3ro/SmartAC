package dev.cocot3ro.smartac.domain.model

import dev.cocot3ro.smartac.data.network.model.HealthResponse

data class HealthDTO(
    val isSetup: Boolean,
    val version: VersionDTO
)

fun HealthResponse.toDto(): HealthDTO = HealthDTO(
    isSetup = this.setup,
    version = this.version.toDto()
)