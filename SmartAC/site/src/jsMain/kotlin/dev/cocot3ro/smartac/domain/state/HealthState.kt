package dev.cocot3ro.smartac.domain.state

import dev.cocot3ro.smartac.domain.model.HealthDTO

sealed class HealthState {
    data object Idle : HealthState()
    data class Healthy(val data: HealthDTO) : HealthState()
    data class Unhealthy(val cause: Throwable?) : HealthState()
}