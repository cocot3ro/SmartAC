package dev.cocot3ro.smartac.domain.usecase

import android.util.Log
import dev.cocot3ro.smartac.data.datastore.DatastoreRepository
import dev.cocot3ro.smartac.data.network.NetworkRepository
import dev.cocot3ro.smartac.domain.model.Status
import dev.cocot3ro.smartac.network.http.model.AcStateModel
import dev.cocot3ro.smartac.network.http.model.AcStatusModel
import dev.cocot3ro.smartac.network.http.model.WebSocketMessage
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single

@Single
class ConnectUseCase(
    @Provided private val networkRepository: NetworkRepository,
    @Provided private val datastoreRepository: DatastoreRepository
) {
    suspend operator fun invoke() {
        datastoreRepository.clear()
        datastoreRepository.saveStatus(Status.CONNECTING)

        networkRepository.connectWebsocket()
            .onCompletion {
                datastoreRepository.clear()
                datastoreRepository.saveStatus(status = Status.DISCONNECTED)
            }
            .catch { ex: Throwable ->
                Log.e("ConnectUseCase", "WebSocket connection error", ex)
                datastoreRepository.clear()
                datastoreRepository.saveStatus(Status.CONNECTION_ERROR)
            }
            .collect { message: WebSocketMessage ->
                when (message) {
                    is AcStateModel -> {
                        datastoreRepository.saveState(
                            stateModel = message,
                            status = Status.ONLINE
                        )
                    }

                    is AcStatusModel -> {
                        if (message == AcStatusModel.OFFLINE) {
                            datastoreRepository.clear()
                        }

                        datastoreRepository.saveStatus(status = Status.valueOf(value = message.name))
                    }
                }
            }
    }
}
