package dev.cocot3ro.smartac.ui.context

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import dev.cocot3ro.smartac.data.network.NetworkRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinViewModel
import org.koin.core.annotation.Provided
import kotlin.time.Duration.Companion.minutes

data class AuthContext(
    val isAuthenticated: Boolean?,
    val login: (String) -> Unit,
    val logout: () -> Unit
)

val LocalAuthContext: ProvidableCompositionLocal<AuthContext> = compositionLocalOf {
    error("Use inside a AuthContextProvider")
}

@KoinViewModel
class AuthViewModel(
    @Provided private val networkRepository: NetworkRepository
) : ViewModel() {

    private val _authenticated: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val authenticated: StateFlow<Boolean?> = _authenticated
        .onStart {
            viewModelScope.launch {
                while (true) {
                    if (_authenticated.value != false) {
                        refresh()
                    }

                    delay(5.minutes)
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = null
        )

    fun login(password: String) {
        viewModelScope.launch {
            if (networkRepository.login(password)) {
                _authenticated.value = true
            } else {
                clear()
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            networkRepository.logout()
            clear()
        }
    }

    private suspend fun refresh() {
        if (networkRepository.checkSession()) {
            _authenticated.value = true
        } else {
            clear()
        }
    }

    private fun clear() {
        _authenticated.value = false
    }
}

@Suppress("ParamsComparedByRef")
@Composable
fun AuthContextProvider(
    viewModel: AuthViewModel = koinViewModel(),
    content: @Composable () -> Unit
) {

    val isAuthenticated: Boolean? by viewModel.authenticated.collectAsStateWithLifecycle()

    val authContext = AuthContext(
        isAuthenticated = isAuthenticated,
        login = viewModel::login,
        logout = viewModel::logout
    )

    CompositionLocalProvider(
        value = LocalAuthContext provides authContext,
        content = content
    )
}
