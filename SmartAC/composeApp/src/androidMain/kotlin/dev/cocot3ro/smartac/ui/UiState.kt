package dev.cocot3ro.smartac.ui

import androidx.compose.runtime.Immutable
import dev.cocot3ro.smartac.domain.model.AcState

@Immutable
sealed interface UiState {
    @Immutable
    data object Idle : UiState

    @Immutable
    data object Loading : UiState

    @Immutable
    data object DeviceOffline : UiState

    @Immutable
    data object Disconnected : UiState

    @Immutable
    data class Success(val state: AcState) : UiState

    @Immutable
    data object ConnectionError : UiState

    @Immutable
    data class Error(val message: String, val cause: Throwable) : UiState
}
