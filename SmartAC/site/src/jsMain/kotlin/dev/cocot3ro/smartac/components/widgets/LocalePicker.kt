package dev.cocot3ro.smartac.components.widgets

import androidx.compose.runtime.Composable
import androidx.compose.web.events.SyntheticMouseEvent
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.style.toAttrs
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.cocot3ro.smartac.components.icons.FlagIcon
import dev.cocot3ro.smartac.localization.supportedLocales
import io.github.skeptick.libres.LibresSettings
import kotlinx.browser.localStorage
import kotlinx.browser.window
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div

val LocalePickerBtnStyles = CssStyle {
    base {
        Modifier.styleModifier {
            property("color", "light-dark(var(--tblr-dark), var(--tblr-light)) !important")
        }
    }

    hover {
        Modifier.styleModifier {
            property(
                propertyName = "border",
                value = "var(--tblr-btn-border-width) solid transparent !important"
            )
            property(
                propertyName = "background",
                value = "color-mix(in srgb, var(--tblr-btn-hover-bg) 10%, transparent) !important"
            )
        }
    }
}

@Composable
fun LocalePicker(modifier: Modifier = Modifier) {

    Box(modifier = modifier) {
        Div(attrs = { classes("dropdown") }) {

            val colorMode = ColorMode.current

            Button(attrs = LocalePickerBtnStyles.toAttrs {
                classes(
                    "btn", "dropdown-toggle", "btn-sm",
                    if (colorMode.isDark) "btn-ghost-dark" else "btn-ghost-light"
                )
                attr("data-bs-toggle", "dropdown")
            }) {
                FlagIcon(LibresSettings.currentLocale!!.language)
            }

            Div(attrs = {
                classes("dropdown-menu")
            }) {
                supportedLocales.forEach { (code, lang) ->
                    A(attrs = {
                        classes("dropdown-item")
                        onClick { ev: SyntheticMouseEvent ->
                            ev.preventDefault()
                            localStorage.setItem("lang", code)
                            window.location.reload()
                        }
                    }) {
                        FlagIcon(languageCode = code)
                        SpanText(lang)
                    }
                }
            }
        }
    }
}
