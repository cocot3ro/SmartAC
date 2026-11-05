package dev.cocot3ro.smartac.components.layouts

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.components.text.SpanText
import dev.cocot3ro.smartac.context.AuthContext
import dev.cocot3ro.smartac.context.LocalAuthContext

@Suppress("ModifierRequired")
@Layout
@Composable
fun AuthLayout(
    ctx: PageContext,
    content: @Composable () -> Unit
) {
    @Suppress("ExplicitDependencies")
    val authContext: AuthContext = LocalAuthContext.current

    when (authContext.isAuthenticated) {
        true -> content.invoke()

        false -> ctx.router.tryRoutingTo(pathQueryAndFragment = "/login/")

        null -> SpanText("Trying login")
    }
}
