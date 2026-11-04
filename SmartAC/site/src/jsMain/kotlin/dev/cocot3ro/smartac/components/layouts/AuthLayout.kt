package dev.cocot3ro.smartac.components.layouts

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.layout.Layout
import dev.cocot3ro.smartac.presentation.context.LocalAuthContext

@Layout
@Composable
fun AuthLayout(
    ctx: PageContext,
    content: @Composable () -> Unit
) {
    val authContext = LocalAuthContext.current
    println("isAuthenticated: ${authContext.isAuthenticated}")

    content.invoke()
}
