package dev.cocot3ro.smartac

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.varabyte.kobweb.compose.css.ScrollBehavior
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.minHeight
import com.varabyte.kobweb.compose.ui.modifiers.scrollBehavior
import com.varabyte.kobweb.core.App
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.navigation.Router
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
import dev.cocot3ro.smartac.context.LocalAuthContext
import dev.cocot3ro.smartac.di.KoinApp
import org.jetbrains.compose.web.css.vh
import org.koin.compose.KoinApplication
import org.koin.plugin.module.dsl.koinConfiguration

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
    }
}

@Suppress("ModifierRequired")
@App
@Composable
fun AppEntry(content: @Composable () -> Unit) {
    KoinApplication(configuration = koinConfiguration<KoinApp>()) {
        SilkApp {
            AuthContextProvider {
                val colorMode = ColorMode.current

                LaunchedEffect(colorMode) {
                    colorMode.saveToLocalStorage(COLOR_MODE_KEY)
                }

                Surface(modifier = SmoothColorStyle.toModifier().minHeight(100.vh)) {
                    content()
                }
            }
        }
    }
}
