package dev.cocot3ro.smartac.components.notifications

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.ariaLabel
import com.varabyte.kobweb.compose.css.cursor
import dev.cocot3ro.smartac.notifications.Notification
import dev.cocot3ro.smartac.notifications.NotificationManager
import dev.cocot3ro.smartac.notifications.NotificationType
import kotlinx.coroutines.delay
import org.jetbrains.compose.web.attributes.AttrsScope
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.backgroundColor
import org.jetbrains.compose.web.css.border
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.fontSize
import org.jetbrains.compose.web.css.maxWidth
import org.jetbrains.compose.web.css.padding
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vw
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H3
import org.jetbrains.compose.web.dom.Header
import org.jetbrains.compose.web.dom.Main
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLDivElement

//@Composable
//@Suppress("ModifierRequired")
//fun NotificationItem(
//    notification: Notification,
//    attrs: (AttrsScope<HTMLDivElement>.() -> Unit)? = null
//) {
//
//    LaunchedEffect(notification.id) {
//        if (notification.duration.isPositive()) {
//            delay(notification.duration)
//            NotificationManager.dismiss(notification.id)
//        }
//    }
//
//    val accent = when (notification.type) {
//        NotificationType.SUCCESS -> Color("#2fb344")
//        NotificationType.ERROR -> Color("#d63939")
//        NotificationType.WARNING -> Color("#f59f00")
//        NotificationType.INFO -> Color("#206bc4")
//    }
//
//    val symbol = when (notification.type) {
//        NotificationType.SUCCESS -> "✓"
//        NotificationType.ERROR -> "✕"
//        NotificationType.WARNING -> "!"
//        NotificationType.INFO -> "i"
//    }
//
//    val colorMode = ColorMode.current
//
//    Div(
//        attrs = {
//            style {
//                display(DisplayStyle.Flex)
//                alignItems(AlignItems.FlexStart)
//                gap(0.75.cssRem)
//                width(22.cssRem)
//                maxWidth(90.vw)
//                padding(1.cssRem)
//                backgroundColor(colorMode.toSitePalette().nearBackground)
//                border(1.px, LineStyle.Solid, colorMode.toPalette().border)
//                borderRadius(0.5.cssRem)
//                boxShadow(
//                    offsetX = 0.px,
//                    offsetY = 3.px,
//                    blurRadius = 12.px,
//                    color = Color("#00000020")
//                )
//            }
//
//            attrs?.invoke(this)
//        }
//    ) {
//        // Icono
//        Div(
//            attrs = {
//                style {
//                    color(accent)
//                    fontSize(1.25.cssRem)
//                    fontWeight("bold")
//                    minWidth(1.5.cssRem)
//                }
//            }
//        ) {
//            Text(symbol)
//        }
//
//        // Contenido
//        Div(
//            attrs = {
//                style {
//                    flex(1)
//                    minWidth(0.px)
//                }
//            }
//        ) {
//            Header(
//                attrs = {
//                    style {
//                        fontWeight("600")
//                        fontSize(0.95.cssRem)
//                        marginBottom(0.25.cssRem)
//                    }
//                }
//            ) {
//                Text(notification.title)
//            }
//
//            Main(
//                attrs = {
//                    style {
//                        color(Color("#626976"))
//                        fontSize(0.875.cssRem)
//                    }
//                }
//            ) {
//                Text(notification.message)
//            }
//        }
//
//        // Botón de cierre
//        Button(
//            attrs = {
//                onClick {
//                    NotificationManager.dismiss(notification.id)
//                }
//
//                style {
//                    backgroundColor(Color.transparent)
//                    border(0.px, LineStyle.None, Color.transparent)
//                    color(Color("#626976"))
//                    cursor(Cursor.Pointer)
//                    fontSize(1.1.cssRem)
//                    padding(0.px)
//                }
//            }
//        ) {
//            Text("×")
//        }
//    }
//}

@Composable
@Suppress("ModifierRequired")
fun NotificationItem(
    notification: Notification,
    attrs: (AttrsScope<HTMLDivElement>.() -> Unit)? = null
) {

    LaunchedEffect(notification.id) {
        if (notification.duration.isPositive()) {
            delay(notification.duration)
            NotificationManager.dismiss(notification.id)
        }
    }

    val accentColor: String = when (notification.type) {
        NotificationType.SUCCESS -> "green"
        NotificationType.ERROR -> "red"
        NotificationType.WARNING -> "orange"
        NotificationType.INFO -> "azure"
    }

    Div(attrs = {
        classes("card")

        style {
            width(22.cssRem)
            maxWidth(90.vw)
        }

        attrs?.invoke(this)
    }) {
        Header(attrs = {
            classes("card-header")
        }) {
            H3(attrs = {
                classes("card-title")
            }) {
                Text(notification.title)
            }

            Div(attrs = {
                classes("card-actions")
            }) {
                Button(
                    attrs = {
                        onClick { ev ->
                            ev.preventDefault()
                            NotificationManager.dismiss(notification.id)
                        }

                        style {
                            backgroundColor(Color.transparent)
                            border(0.px, LineStyle.None, Color.transparent)
                            color(Color("#626976"))
                            cursor(Cursor.Pointer)
                            fontSize(1.1.cssRem)
                            padding(0.px)

                        }

                        // TODO: Localize
                        ariaLabel("Close")
                    }
                ) {
                    Text("×")
                }
            }
        }

        Div(attrs = {
            classes("card-status-start", "bg-$accentColor")
        })

        Main(attrs = {
            classes("card-body")
        }) {
            Text(notification.message)
        }
    }
}
