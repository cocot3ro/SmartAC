package dev.cocot3ro.smartac.components.widgets

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.AttrBuilderContext
import org.jetbrains.compose.web.dom.TagElement
import org.w3c.dom.HTMLElement

@Suppress("ModifierRequired")
@Composable
fun Figure(
    attrs: AttrBuilderContext<HTMLElement>? = null,
    content: @Composable () -> Unit
) {
    TagElement(
        tagName = "figure",
        applyAttrs = attrs ?: {}
    ) {
        content()
    }
}

@Suppress("ModifierRequired")
@Composable
fun FigCaption(
    attrs: AttrBuilderContext<HTMLElement>? = null,
    content: @Composable () -> Unit
) {
    TagElement(
        tagName = "figcaption",
        applyAttrs = attrs ?: {}
    ) {
        content()
    }
}