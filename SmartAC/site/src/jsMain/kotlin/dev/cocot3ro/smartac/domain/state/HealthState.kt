package dev.cocot3ro.smartac.domain.state

import dev.cocot3ro.smartac.data.network.model.HealthResponse

sealed class HealthState {
    data object Idle : HealthState()
    data class Healthy(val data: HealthResponse) : HealthState()
    data class Unhealthy(val cause: Throwable?) : HealthState()
}