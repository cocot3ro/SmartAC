package dev.cocot3ro.smartac

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.ScrollBehavior
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.minHeight
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.right
import com.varabyte.kobweb.compose.ui.modifiers.scrollBehavior
import com.varabyte.kobweb.compose.ui.modifiers.top
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.core.App
import com.varabyte.kobweb.silk.SilkApp
import com.varabyte.kobweb.silk.components.layout.Surface
import com.varabyte.kobweb.silk.init.InitSilk
import com.varabyte.kobweb.silk.init.InitSilkContext
import com.varabyte.kobweb.silk.init.registerStyleBase
import com.varabyte.kobweb.silk.style.common.SmoothColorStyle
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.varabyte.kobweb.silk.theme.colors.loadFromLocalStorage
import com.varabyte.kobweb.silk.theme.colors.saveToLocalStorage
import com.varabyte.kobweb.silk.theme.colors.systemPreference
import dev.cocot3ro.smartac.context.AuthContextProvider
import dev.cocot3ro.smartac.context.HealthContextProvider
import dev.cocot3ro.smartac.di.KoinApp
import dev.cocot3ro.smartac.localization.defaultLocale
import io.github.skeptick.libres.LibresLocale
import io.github.skeptick.libres.LibresSettings
import io.github.skeptick.libres.getPlatformDefaultLibresLocale
import kotlinx.browser.localStorage
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.css.vw
import org.koin.compose.KoinApplication
import org.koin.plugin.module.dsl.koinConfiguration
import web.dom.document

private const val COLOR_MODE_KEY = "smartac:colorMode"

@InitSilk
fun initColorMode(ctx: InitSilkContext) {
    ctx.config.initialColorMode =
        ColorMode.loadFromLocalStorage(COLOR_MODE_KEY) ?: ColorMode.systemPreference
}

@InitSilk
fun initStyles(ctx: InitSilkContext) {
    ctx.stylesheet.apply {
        registerStyleBase("body") { Modifier.scrollBehavior(ScrollBehavior.Smooth) }
        registerStyleBase(".sr-only") {
            Modifier.position(Position.Absolute)
                .width(1.px)
                .top(200.vh)
                .right(200.vw)
                .overflow(Overflow.Hidden)
        }
    }
}

@Suppress("ModifierRequired")
@App
@Composable
fun AppEntry(content: @Composable () -> Unit) {
    LibresSettings.setLocaleProvider {
        localStorage.getItem("lang")
            ?.let { lang -> LibresLocale(language = lang, script = null, region = null) }
            ?: getPlatformDefaultLibresLocale()
            ?: defaultLocale
    }

    document.documentElement.lang = LibresSettings.currentLocale!!.language

    KoinApplication(configuration = koinConfiguration<KoinApp>()) {
        SilkApp {
            HealthContextProvider {
                AuthContextProvider {
                    val colorMode: ColorMode = ColorMode.current

                    LaunchedEffect(colorMode) {
                        colorMode.saveToLocalStorage(COLOR_MODE_KEY)
                    }

                    Surface(modifier = SmoothColorStyle.toModifier().minHeight(100.vh)) {
                        content.invoke()
                    }
                }
            }
        }
    }
}
