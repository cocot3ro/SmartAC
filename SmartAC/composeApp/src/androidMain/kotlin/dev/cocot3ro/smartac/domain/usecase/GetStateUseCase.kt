package dev.cocot3ro.smartac.domain.usecase

import androidx.datastore.preferences.core.Preferences
import dev.cocot3ro.smartac.data.datastore.DatastoreConstants.Keys.CURRENT_TEMP
import dev.cocot3ro.smartac.data.datastore.DatastoreConstants.Keys.ERROR_CODE
import dev.cocot3ro.smartac.data.datastore.DatastoreConstants.Keys.FAN_SPEED
import dev.cocot3ro.smartac.data.datastore.DatastoreConstants.Keys.IS_FAN_AUTO
import dev.cocot3ro.smartac.data.datastore.DatastoreConstants.Keys.MODE
import dev.cocot3ro.smartac.data.datastore.DatastoreConstants.Keys.POWER
import dev.cocot3ro.smartac.data.datastore.DatastoreConstants.Keys.STATUS
import dev.cocot3ro.smartac.data.datastore.DatastoreConstants.Keys.TARGET_TEMP
import dev.cocot3ro.smartac.data.datastore.DatastoreRepository
import dev.cocot3ro.smartac.domain.model.AcMode
import dev.cocot3ro.smartac.domain.model.AcState
import dev.cocot3ro.smartac.domain.model.FanSpeed
import dev.cocot3ro.smartac.domain.model.Status
import dev.cocot3ro.smartac.domain.state.DomainFlowState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single

@Single
class GetStateUseCase(
    @Provided private val datastoreRepository: DatastoreRepository
) {

    operator fun invoke(): Flow<DomainFlowState> = datastoreRepository.getLocalState()
        .map { prefs: Map<Preferences.Key<*>, Any> ->
            when ((prefs[STATUS] as? String)?.let (Status::valueOf)) {
                null -> DomainFlowState.Idle

                Status.ONLINE -> {
                    val power: Boolean =
                        (prefs[POWER] as? Boolean) ?: return@map DomainFlowState.Loading

                    val isFanAuto: Boolean =
                        (prefs[IS_FAN_AUTO] as? Boolean) ?: return@map DomainFlowState.Loading

                    val fanSpeed: FanSpeed =
                        ((prefs[FAN_SPEED] as? String)?.let { FanSpeed.valueOf(it) })
                            ?: return@map DomainFlowState.Loading

                    val mode: AcMode = ((prefs[MODE] as? String)?.let { AcMode.valueOf(it) })
                        ?: return@map DomainFlowState.Loading

                    val currentTemp: Int =
                        (prefs[CURRENT_TEMP] as? Int) ?: return@map DomainFlowState.Loading

                    val targetTemp: Int =
                        (prefs[TARGET_TEMP] as? Int) ?: return@map DomainFlowState.Loading

                    val errorCode: UByte? = (prefs[ERROR_CODE] as? Int)?.toUByte()

                    DomainFlowState.Success(
                        acState = AcState(
                            power = power,
                            isFanAuto = isFanAuto,
                            fanSpeed = fanSpeed,
                            mode = mode,
                            currentTemp = currentTemp,
                            targetTemp = targetTemp,
                            errorCode = errorCode
                        )
                    )
                }

                Status.OFFLINE -> DomainFlowState.DeviceOffline
                Status.CONNECTING -> DomainFlowState.Connecting
                Status.CONNECTION_ERROR -> DomainFlowState.ConnectionError
                Status.DISCONNECTED -> DomainFlowState.Disconnected
            }
        }
}
