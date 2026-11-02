package dev.cocot3ro.smartac

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.cocot3ro.smartac.di.KoinApp
import dev.cocot3ro.smartac.ui.Router
import dev.cocot3ro.smartac.ui.context.AuthContextProvider
import dev.cocot3ro.smartac.ui.theme.SmartAcAdminTheme
import org.koin.compose.KoinApplication
import org.koin.plugin.module.dsl.koinConfiguration

@Suppress("ModifierRequired")
@Composable
fun App() {
    KoinApplication(configuration = koinConfiguration<KoinApp>()) {
        SmartAcAdminTheme {
            AuthContextProvider {
                Router(modifier = Modifier.fillMaxSize())
            }
        }
    }
}
