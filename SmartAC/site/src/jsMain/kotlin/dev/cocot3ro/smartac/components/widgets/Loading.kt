package dev.cocot3ro.smartac.components.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.maxHeight
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div

val LogoStyle = CssStyle.base {
    Modifier.maxHeight(100.px)
}

@Suppress("ModifierRequired")
@Composable
fun Loading(label: String?, showLogo: Boolean) {
    Div(attrs = {
        classes("empty", "text-center")
    }) {
        if (showLogo) {
            Div(attrs = {
                classes("mb-3")
            }) {
                // TODO: Logo
                Image(
                    src = "/logo-no-text.svg",
                    description = ""
                )
            }
        }

        Div(attrs = {
            classes("text-secondary", "mb-3")
        }) {
            // TODO: Localize
            SpanText(label ?: "Loading")
        }

        Div(attrs = {
            classes("progress", "progress-sm")
        }) {
            Div(attrs = {
                classes("progress-bar", "progress-bar-indeterminate")
            })
        }
    }
}
