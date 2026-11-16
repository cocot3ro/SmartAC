package dev.cocot3ro.smartac.components.sections

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.Main

@Suppress("ModifierRequired")
@Composable
fun SiteContainer(
    content: @Composable () -> Unit
) {
    Main(attrs = {
        classes("container-xl", "py-3", "min-w-0", "overflow-x-auto")
    }) {
        content.invoke()
    }
}