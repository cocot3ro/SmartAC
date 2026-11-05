package dev.cocot3ro.smartac.context

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import dev.cocot3ro.smartac.domain.repository.AuthRepository
import org.koin.compose.koinInject

data class AuthContext(
    val isAuthenticated: Boolean?,
    val login: suspend (String) -> Unit,
    val logout: suspend () -> Unit
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

    val authContext = AuthContext(
        isAuthenticated = authRepository.authenticated,
        login = authRepository::login,
        logout = authRepository::logout
    )

    CompositionLocalProvider(
        value = LocalAuthContext provides authContext,
        content = content
    )
}
