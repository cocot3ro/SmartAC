package dev.cocot3ro.smartac.components.layouts

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.core.layout.Layout
import dev.cocot3ro.smartac.context.LocalAuthContext
import dev.cocot3ro.smartac.context.LocalHealthContext
import dev.cocot3ro.smartac.domain.state.HealthState
import dev.cocot3ro.smartac.pages.LoadingPage
import dev.cocot3ro.smartac.pages.LoginPage
import dev.cocot3ro.smartac.pages.SetupPage
import dev.cocot3ro.smartac.pages.UnhealthyPage

@Suppress("ModifierRequired", "ExplicitDependencies")
@Layout
@Composable
fun RouterLayout(
    content: @Composable () -> Unit
) {
    val healthState: HealthState = LocalHealthContext.current.healthState
    val authenticated: Boolean? = LocalAuthContext.current.isAuthenticated

    when (healthState) {
        HealthState.Idle -> LoadingPage("Loading health")

        is HealthState.Unhealthy -> UnhealthyPage()

        is HealthState.Healthy -> if (!healthState.data.isSetup) {
            SetupPage()
        } else when (authenticated) {
            null -> LoadingPage("Loading authentication")

            false -> LoginPage()

            else -> content.invoke()
        }
    }
}
