package dev.cocot3ro.smartac.context

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import dev.cocot3ro.smartac.data.network.NetworkRepository
import dev.cocot3ro.smartac.domain.repository.AuthRepository
import org.koin.compose.koinInject

data class AuthContext(
    val isAuthenticated: Boolean?,
    val login: (String) -> Unit,
    val logout: () -> Unit
)

val LocalAuthContext: ProvidableCompositionLocal<AuthContext> = compositionLocalOf {
    error("Use inside a AuthContextProvider")
}

@Suppress("ParamsComparedByRef")
@Composable
fun AuthContextProvider(
    authRepository: AuthRepository = koinInject(),
    content: @Composable () -> Unit
) {

    val isAuthenticated: Boolean? by authRepository.authenticated
    val authContext = AuthContext(
        isAuthenticated = isAuthenticated,
        login = authRepository::login,
        logout = authRepository::logout
    )

    CompositionLocalProvider(
        value = LocalAuthContext provides authContext,
        content = content
    )
}
