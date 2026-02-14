package dev.cocot3ro.smartac.domain.usecase

import androidx.compose.runtime.Stable
import dev.cocot3ro.smartac.data.network.NetworkRepository
import dev.cocot3ro.smartac.domain.model.AcControl
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single

@Single
@Stable
class ControlUseCase(
    @Provided private val networkRepository: NetworkRepository
) {

    suspend fun setPower(power: AcControl.Power) {
        networkRepository.sendPower(power)
    }

    suspend fun setTemperature(temperature: AcControl.Temperature) {
        networkRepository.sendTemperature(temperature)
    }

    suspend fun setMode(mode: AcControl.Mode) {
        networkRepository.sendMode(mode)
    }

    suspend fun setFanSpeed(fanSpeed: AcControl.FanSpeed) {
        networkRepository.sendFanSpeed(fanSpeed)
    }
}