package dev.cocot3ro.smartac.components.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.toAttrs
import org.jetbrains.compose.web.css.px
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
        // Tabler styles apply a margin bottom of 1px to <figure> tags.
        applyAttrs = Modifier.margin(0.px).toAttrs(attrs)
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