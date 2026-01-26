package dev.cocot3ro.smartac

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import dev.cocot3ro.smartac.domain.usecase.ConnectUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single

@Single(createdAtStart = true)
class AppLifecycleObserver(
    @Provided private val connectUseCase: ConnectUseCase
) : DefaultLifecycleObserver {

    private val coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private var connectionJob: Job? = null

    override fun onStart(owner: LifecycleOwner) {
        connectionJob = coroutineScope.launch(context = Dispatchers.IO) {
            connectUseCase.invoke()
        }
    }

    override fun onStop(owner: LifecycleOwner) {
        connectionJob?.cancel()
        connectionJob = null
    }
}
