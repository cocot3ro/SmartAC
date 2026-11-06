package dev.cocot3ro.smartac.components.layouts

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.components.text.SpanText
import dev.cocot3ro.smartac.context.AuthContext
import dev.cocot3ro.smartac.context.LocalAuthContext
import dev.cocot3ro.smartac.data.network.NetworkRepository
import org.koin.compose.koinInject

@Suppress("ModifierRequired", "ExplicitDependencies")
@Layout
@Composable
fun RouterLayout(
    ctx: PageContext,
    content: @Composable () -> Unit
) {
    val networkRepository: NetworkRepository = koinInject()
    val authContext: AuthContext = LocalAuthContext.current

    val authenticated = authContext.isAuthenticated
    if (authenticated == false) {
        ctx.router.tryRoutingTo(pathQueryAndFragment = "/login/")
    } else if (authenticated == null) {
        SpanText("Trying login")
    } else {
        content.invoke()
    }
}
