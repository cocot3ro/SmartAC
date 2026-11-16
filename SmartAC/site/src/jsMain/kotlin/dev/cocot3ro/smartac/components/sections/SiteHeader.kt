package dev.cocot3ro.smartac.components.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.ariaLabel
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.classNames
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.toAttrs
import dev.cocot3ro.smartac.components.widgets.Figure
import dev.cocot3ro.smartac.components.widgets.LocalePicker
import dev.cocot3ro.smartac.components.widgets.ThemeSwitcher
import org.jetbrains.compose.web.attributes.href
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Header
import org.jetbrains.compose.web.dom.I
import org.jetbrains.compose.web.dom.Text

val LogoStyles = CssStyle {
    base {
        Modifier.fontSize(1.1.cssRem)
            .fontWeight(500)
    }

    cssRule("img") {
        Modifier.margin(right = 0.8.cssRem)
    }
}

@Suppress("ModifierRequired")
@Composable
fun SiteHeader() {
    Header(attrs = {
        classes("navbar", "navbar-expand-md", "d-print-none")
    }) {
        Div(attrs = {
            classes("container-xl")
        }) {
            Button(attrs = {
                classes("navbar-toggler")
                attr("data-bs-toggle", "collapse")
                attr("data-bs-target", "#navbar-menu")
                attr("aria-controls", "navbar-menu")
                attr("aria-expanded", "false")
                ariaLabel("Toggle navigation")
            }) {
                I(attrs = {
                    classes("navbar-toggler-icon")
                })
            }

            Div(attrs = {
                classes("navbar-brand", "navbar-brand-autodark", "pe-0", "pe-md-3")
            }) {
                A(attrs = {
                    classes("nav-link")
                    href("/")
                }) {
                    Figure(attrs = LogoStyles.toAttrs()) {
                        // TODO: Logo
                        Image(
                            modifier = Modifier.classNames("navbar-brand-image"),
                            src = "/favicon.ico",
                            alt = "Logo",
                            width = 40,
                            height = 40
                        )
                    }

                    // TODO: Localize
                    Text(value = "SmartAC")
                }
            }

            Div(attrs = {
                classes("navbar-nav", "flex-row", "order-md-last")
            }) {
                Div(attrs = {
                    classes("d-none", "d-md-flex")
                }) {
                    Div(attrs = { classes("nav-item") }) {
                        LocalePicker()
                    }

                    Div(attrs = { classes("nav-item") }) {
                        ThemeSwitcher()
                    }
                }
            }
        }
    }
}
