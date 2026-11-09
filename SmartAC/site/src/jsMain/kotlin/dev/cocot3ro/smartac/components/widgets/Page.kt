package dev.cocot3ro.smartac.components.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.GridEntry
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.gridTemplateRows
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.fr
import org.jetbrains.compose.web.dom.Div

val PageStyle = CssStyle.base {
    Modifier.display(DisplayStyle.Grid)
        .gridTemplateRows {
            size(GridEntry.TrackSize.Auto)
            size(1.fr)
            size(GridEntry.TrackSize.Auto)
        }
}

@Composable
fun Page(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Div(attrs = PageStyle.toModifier().then(modifier).toAttrs()) {
        content()
    }
}
