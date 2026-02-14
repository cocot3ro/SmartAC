package dev.cocot3ro.smartac.core.state

import dev.cocot3ro.smartac.core.control.AcControl
import dev.cocot3ro.smartac.core.control.toAcMode
import dev.cocot3ro.smartac.core.control.toIrFanSpeed
import dev.cocot3ro.smartac.core.display.DisplayFanSpeed
import dev.cocot3ro.smartac.core.display.DisplayFrame
import dev.cocot3ro.smartac.core.display.toAcFanSpeed
import dev.cocot3ro.smartac.core.display.toAcMode
import dev.cocot3ro.smartac.core.ir.Commands
import dev.cocot3ro.smartac.core.ir.IrFanSpeed
import dev.cocot3ro.smartac.core.ir.TEMP_MAX
import dev.cocot3ro.smartac.core.ir.TEMP_MIN
import dev.cocot3ro.smartac.core.ir.createIrFrame
import dev.cocot3ro.smartac.network.http.model.AcControlModel
import dev.cocot3ro.smartac.network.http.model.toAcControl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

//Singleton
class StateRepository {
    private val _state: MutableStateFlow<AcFlowState> = MutableStateFlow(AcFlowState.Idle)
    val state: StateFlow<AcFlowState> = _state.asStateFlow()

    @OptIn(ExperimentalUnsignedTypes::class)
    fun updateState(ds: DisplayFrame) {
        _state.value = AcFlowState.Online(
            AcState(
                power = ds.power,
                mode = ds.mode.toAcMode(),
                currentTemp = ds.currentTemp,
                targetTemp = ds.targetTemp,
                isFanAuto = ds.isFanAuto,
                isFanStop = ds.fanSpeed == DisplayFanSpeed.STOP,
                fanSpeed = ds.fanSpeed.toAcFanSpeed(),
                errorCode = ds.errorCode
            )
        )
    }

    fun updateStatus(status: AcStatus) {
        when (status) {
            AcStatus.ONLINE -> _state.value = AcFlowState.Idle
            AcStatus.OFFLINE -> _state.value = AcFlowState.Offline
        }
    }

    @OptIn(ExperimentalUnsignedTypes::class)
    fun withMode(mode: AcControlModel.Mode): UByteArray? {
        _state.value.let { currentState: AcFlowState ->
            if (currentState !is AcFlowState.Online) return null

            mode.toAcControl().toAcMode().let { mode: AcMode ->
                if (currentState.acState.mode == mode) return null

                return createIrFrame(
                    powerToggle = false,
                    mode = mode.toIrMode(),
                    temp = mode.defaultTemp,
                    fanSpeed = mode.defaultFanSpeed,
                    cmd = Commands.MODE
                )
            }
        }
    }

    @OptIn(ExperimentalUnsignedTypes::class)
    fun withTemp(temp: AcControlModel.Temperature): UByteArray? {
        _state.value.let { currentState: AcFlowState ->
            if (currentState !is AcFlowState.Online) return null

            currentState.acState.let { state: AcState ->

                if (state.mode == AcMode.FAN || state.mode == AcMode.DRY) {
                    return null
                }

                temp.toAcControl().let { temp: AcControl.Temperature ->

                    if ((temp as? AcControl.Temperature.Set)?.value == state.targetTemp) {
                        return null
                    }

                    val temperatureValue: Int? = when (temp) {
                        is AcControl.Temperature.Set -> {
                            temp.value.coerceIn(TEMP_MIN, TEMP_MAX).takeUnless { newTemp: Int ->
                                newTemp == state.targetTemp
                            }
                        }

                        AcControl.Temperature.Increase -> {
                            if (state.targetTemp == TEMP_MAX) null
                            else state.targetTemp + 1
                        }

                        AcControl.Temperature.Decrease -> {
                            if (state.targetTemp == TEMP_MIN) null
                            else state.targetTemp - 1
                        }
                    }

                    if (temperatureValue == null) return null

                    return createIrFrame(
                        powerToggle = false,
                        mode = state.mode.toIrMode(),
                        temp = temperatureValue,
                        fanSpeed = if (state.isFanAuto) {
                            IrFanSpeed.AUTO
                        } else state.fanSpeed.toIrFanSpeed(),
                        cmd = Commands.TEMPERATURE
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalUnsignedTypes::class)
    fun withPower(power: AcControlModel.Power): UByteArray? {
        _state.value.let { currentState: AcFlowState ->
            if (currentState !is AcFlowState.Online) return null

            currentState.acState.let { state: AcState ->
                when (power.toAcControl()) {
                    AcControl.Power.POWER_ON -> if (state.power) return null
                    AcControl.Power.POWER_OFF -> if (!state.power) return null
                    AcControl.Power.TOGGLE -> {}
                }

                return createIrFrame(
                    powerToggle = true,
                    mode = state.mode.toIrMode(),
                    temp = state.targetTemp,
                    fanSpeed = if (state.isFanAuto) {
                        IrFanSpeed.AUTO
                    } else {
                        state.fanSpeed.toIrFanSpeed()
                    },
                    cmd = Commands.POWER
                )
            }
        }
    }

    @OptIn(ExperimentalUnsignedTypes::class)
    fun withFanSpeed(fanSpeed: AcControlModel.FanSpeed): UByteArray? {
        _state.value.let { currentState: AcFlowState ->
            if (currentState !is AcFlowState.Online) return null

            currentState.acState.let { state: AcState ->
                fanSpeed.toAcControl().let { fanSpeed: AcControl.FanSpeed ->
                    when (fanSpeed) {
                        AcControl.FanSpeed.AUTO -> if (state.isFanAuto) return null
                        AcControl.FanSpeed.LOW -> if (state.fanSpeed == AcFanSpeed.LOW) return null
                        AcControl.FanSpeed.MEDIUM -> if (state.fanSpeed == AcFanSpeed.MEDIUM) return null
                        AcControl.FanSpeed.HIGH -> if (state.fanSpeed == AcFanSpeed.HIGH) return null
                    }

                    return createIrFrame(
                        powerToggle = false,
                        mode = state.mode.toIrMode(),
                        temp = state.targetTemp,
                        fanSpeed = fanSpeed.toIrFanSpeed(),
                        cmd = Commands.FAN_SPEED
                    )
                }
            }
        }
    }
}
