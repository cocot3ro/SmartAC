package dev.cocot3ro.smartac.components.notifications

import androidx.compose.runtime.Composable
import dev.cocot3ro.smartac.notifications.NotificationManager
import org.jetbrains.compose.web.attributes.AttrsScope
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.display
import org.jetbrains.compose.web.css.flexDirection
import org.jetbrains.compose.web.css.gap
import org.jetbrains.compose.web.dom.Div
import org.w3c.dom.HTMLDivElement

@Composable
@Suppress("ModifierRequired")
fun NotificationHost(
    attrs: (AttrsScope<HTMLDivElement>.() -> Unit)? = null
) {
    Div(
        attrs = {
            style {
                display(DisplayStyle.Flex)
                flexDirection(FlexDirection.Column)
                gap(0.75.cssRem)

                property("pointer-events", "none")
            }

            attrs?.invoke(this)
        }
    ) {
        NotificationManager.notifications.forEach { notification ->
            Div(
                attrs = {
                    style {
                        property("pointer-events", "auto")
                    }
                }
            ) {
                NotificationItem(notification)
            }
        }
    }
}