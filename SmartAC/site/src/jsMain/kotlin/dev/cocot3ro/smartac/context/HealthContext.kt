package dev.cocot3ro.smartac.context

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import dev.cocot3ro.smartac.domain.repository.HealthRepository
import dev.cocot3ro.smartac.domain.state.HealthState
import org.koin.compose.koinInject

data class HealthContext(
    val healthState: HealthState,
    val refetchHealth: () -> Unit
)

fun HealthContext.asHealthy(): HealthState.Healthy = this.healthState as HealthState.Healthy

val LocalHealthContext: ProvidableCompositionLocal<HealthContext> = compositionLocalOf {
    error("Use inside a HealthContextProvider")
}

@Suppress("ParamsComparedByRef", "FrequentRecomposition")
@Composable
fun HealthContextProvider(
    healthRepository: HealthRepository = koinInject(),
    content: @Composable () -> Unit
) {
    val healthState: HealthState by healthRepository.health.collectAsState()

    CompositionLocalProvider(
        value = LocalHealthContext provides HealthContext(
            healthState = healthState,
            refetchHealth = healthRepository::refetchHealth
        ),
        content = content
    )
}
