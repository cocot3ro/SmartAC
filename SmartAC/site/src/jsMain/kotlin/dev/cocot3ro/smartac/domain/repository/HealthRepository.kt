package dev.cocot3ro.smartac.domain.repository

import dev.cocot3ro.smartac.data.network.NetworkRepository
import dev.cocot3ro.smartac.data.network.model.HealthResponse
import dev.cocot3ro.smartac.domain.model.toDto
import dev.cocot3ro.smartac.domain.state.HealthState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided
import kotlin.time.Duration.Companion.seconds

@Factory
class HealthRepository(
    @Provided private val networkRepository: NetworkRepository
) {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    private val _health: MutableStateFlow<HealthState> = MutableStateFlow(HealthState.Idle)
    val health: StateFlow<HealthState> = _health
        .onStart {
            fetchHealth()
        }
        .stateIn(
            scope = scope,
            started = SharingStarted.Lazily,
            initialValue = HealthState.Idle
        )

    private fun fetchHealth() {
        scope.launch {
            while (true) {
                networkRepository.getHealth()
                    .onSuccess { response: HealthResponse ->
                        _health.value = HealthState.Healthy(response.toDto())
                    }
                    .onFailure { cause ->
                        _health.value = HealthState.Unhealthy(cause)
                    }

                delay(duration = 15.seconds)
            }
        }
    }
}
