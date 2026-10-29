package dev.cocot3ro.smartac.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
class RouterViewModel : ViewModel(

) {

    private val _health: MutableStateFlow<HealthState> = MutableStateFlow(HealthState.Idle)
    val health: StateFlow<HealthState> = _health
        .onStart { fetchHealth() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = HealthState.Idle
        )

    private fun fetchHealth() {
        viewModelScope.launch {
            _health.value = HealthState.Loading
        }
    }
}
