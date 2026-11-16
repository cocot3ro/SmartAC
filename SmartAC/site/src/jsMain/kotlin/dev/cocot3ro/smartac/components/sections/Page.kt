package dev.cocot3ro.smartac.components.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.GridEntry
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.classNames
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.gridTemplateRows
import com.varabyte.kobweb.compose.ui.modifiers.minHeight
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.ComponentKind
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.CssStyleVariant
import com.varabyte.kobweb.silk.style.addVariantBase
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.fr
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.dom.Div

sealed interface PageKind : ComponentKind

val PageGridStyle = CssStyle.base<PageKind> {
    Modifier
        .display(DisplayStyle.Grid)
        .minHeight(100.vh)
        .gridTemplateRows {
            size(GridEntry.TrackSize.Auto)
            size(1.fr)
            size(GridEntry.TrackSize.Auto)
        }
}

val PageVariant: CssStyleVariant<PageKind> = PageGridStyle.addVariantBase(
    extraModifier = { Modifier.classNames("page") },
    init = { Modifier }
)

val PageCenterVariant: CssStyleVariant<PageKind> = PageGridStyle.addVariantBase(
    extraModifier = { Modifier.classNames("page-center") },
    init = { Modifier }
)

@Composable
@Suppress("ModifierRequired")
fun Page(
    vararg variants: CssStyleVariant<PageKind>,
    content: @Composable () -> Unit
) {
    Div(attrs = PageGridStyle.toModifier(*variants).toAttrs()) {
        content()
    }
}
