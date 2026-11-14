package dev.cocot3ro.smartac.domain.repository

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.cocot3ro.smartac.data.network.NetworkRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided
import kotlin.time.Duration.Companion.minutes

@Factory
class AuthRepository(
    @Provided private val networkRepository: NetworkRepository
) {

    private val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private var _job: Job? = null

    var authenticated: Boolean? by mutableStateOf(null)
        private set

    init {
        start()
    }

    private fun start() {
        _job = scope.launch {
            while (true) {
                if (authenticated != false) {
                    refresh()
                }

                delay(15.minutes)
            }
        }
    }

    suspend fun login(password: String) {
        networkRepository.login(password)
            .onSuccess {
                if (it) {
                    authenticated = true
                    start()
                } else {
                    clear()
                }
            }
            .onFailure {
                clear()
            }
    }

    suspend fun logout() {
        networkRepository.logout()
        clear()
    }

    private suspend fun clear() {
        authenticated = false
        _job?.cancelAndJoin()
        _job = null
    }

    private suspend fun refresh() {
        networkRepository.checkSession()
            .onSuccess {
                if (it) {
                    authenticated = true
                } else {
                    clear()
                }
            }
            .onFailure {
                clear()
            }
    }
}
