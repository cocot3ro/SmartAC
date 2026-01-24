package dev.cocot3ro.smartac.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.cocot3ro.smartac.domain.model.AcControl
import dev.cocot3ro.smartac.domain.state.DomainFlowState
import dev.cocot3ro.smartac.domain.usecase.ControlUseCase
import dev.cocot3ro.smartac.domain.usecase.GetStateUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Provided

@KoinViewModel
class AppViewModel(
    @Provided private val getStateUsecase: GetStateUseCase,
    @Provided private val controlUseCase: ControlUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<UiState> = MutableStateFlow(value = UiState.Idle)
    val state: StateFlow<UiState> = _state
        .onStart { connect() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = UiState.Idle
        )

    private fun connect() {
        _state.value = UiState.Loading

        viewModelScope.launch {
            val start: Long = System.currentTimeMillis()

            getStateUsecase.invoke()
                .flowOn(context = Dispatchers.IO)
                .catch { ex: Throwable ->
                    Log.e("AppViewModel", "Error in getStateUsecase", ex)
                    _state.value = UiState.Error(
                        message = ex.message ?: "Unknown error",
                        cause = ex
                    )
                }
                .collect { domainState: DomainFlowState ->
                    delay(1000 - (System.currentTimeMillis() - start))

                    _state.value = when (domainState) {
                        DomainFlowState.Idle,
                        DomainFlowState.Loading,
                        DomainFlowState.Connecting -> UiState.Loading

                        DomainFlowState.ConnectionError -> UiState.ConnectionError

                        DomainFlowState.DeviceOffline -> UiState.DeviceOffline

                        DomainFlowState.Disconnected -> UiState.Disconnected

                        is DomainFlowState.Success -> UiState.Success(domainState.acState)
                    }
                }
        }
    }

    fun setFanSpeed(fs: AcControl.FanSpeed) {
        viewModelScope.launch(Dispatchers.IO) {
            controlUseCase.setFanSpeed(fs)
        }
    }

    fun setMode(mode: AcControl.Mode) {
        viewModelScope.launch(Dispatchers.IO) {
            controlUseCase.setMode(mode)
        }
    }

    fun setPower(pwr: AcControl.Power) {
        viewModelScope.launch(Dispatchers.IO) {
            controlUseCase.setPower(pwr)
        }
    }

    private fun setTemperature(temp: AcControl.Temperature) {
        viewModelScope.launch(Dispatchers.IO) {
            controlUseCase.setTemperature(temp)
        }
    }

    fun setTemperature(temp: Int) {
        setTemperature(AcControl.Temperature.Set(value = temp))
    }

    fun tempIncrease() {
        setTemperature(AcControl.Temperature.Increase)
    }

    fun tempDecrease() {
        setTemperature(AcControl.Temperature.Decrease)
    }
}
