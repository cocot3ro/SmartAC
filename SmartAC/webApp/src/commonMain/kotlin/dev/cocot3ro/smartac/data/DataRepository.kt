package dev.cocot3ro.smartac.data

import dev.cocot3ro.smartac.data.network.NetworkRepository
import dev.cocot3ro.smartac.data.network.model.HealthResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.isActive
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single
import kotlin.time.Duration.Companion.seconds

@Single
class DataRepository(
    @Provided private val networkRepository: NetworkRepository
) {

    fun observeHealth(): Flow<HealthResponse> = callbackFlow {
        while (isActive) {
            val healthResponse: HealthResponse = networkRepository.getHealth()

            send(healthResponse)

            delay(15.seconds)
        }
    }
}
