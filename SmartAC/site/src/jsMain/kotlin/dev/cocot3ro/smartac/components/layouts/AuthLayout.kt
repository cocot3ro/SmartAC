package dev.cocot3ro.smartac.components.layouts

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.layout.Layout
import dev.cocot3ro.smartac.viewmodel.AuthViewModel
import org.koin.compose.viewmodel.koinViewModel

@Layout(".components.layouts.PageLayout")
@Composable
fun AuthLayout(
    ctx: PageContext,
    content: @Composable () -> Unit
) {
    @Suppress("ExplicitDependencies")
    val viewModel: AuthViewModel = koinViewModel()

    println("ASUHDUIHSAD")

    content.invoke()
}