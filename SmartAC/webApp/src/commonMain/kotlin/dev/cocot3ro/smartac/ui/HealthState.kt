package dev.cocot3ro.smartac.ui

import dev.cocot3ro.smartac.data.network.model.HealthResponse

sealed class HealthState {
    data object Idle : HealthState()
    data object Loading : HealthState()
    data class Success(val data: HealthResponse) : HealthState()
    data class Error(val cause: Throwable) : HealthState()
}
