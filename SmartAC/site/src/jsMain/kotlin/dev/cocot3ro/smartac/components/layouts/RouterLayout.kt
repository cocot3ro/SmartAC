package dev.cocot3ro.smartac.components.layouts

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.components.text.SpanText
import dev.cocot3ro.smartac.context.LocalAuthContext
import dev.cocot3ro.smartac.context.LocalHealthContext
import dev.cocot3ro.smartac.domain.state.HealthState

@Suppress("ModifierRequired", "ExplicitDependencies")
@Layout
@Composable
fun RouterLayout(
    ctx: PageContext,
    content: @Composable () -> Unit
) {
    val authenticated: Boolean? = LocalAuthContext.current.isAuthenticated

    when (val healthState: HealthState = LocalHealthContext.current.healthState) {
        HealthState.Idle -> SpanText("Loading health ...")

        is HealthState.Unhealthy -> ctx.router.tryRoutingTo(pathQueryAndFragment = "/unhealthy/")

        is HealthState.Healthy -> if (!healthState.data.setup) {
            ctx.router.tryRoutingTo(pathQueryAndFragment = "/setup/")
        } else when (authenticated) {
            null -> SpanText("Verifying auth ...")

            false -> ctx.router.tryRoutingTo(pathQueryAndFragment = "/login/")

            else -> content.invoke()
        }
    }
}
