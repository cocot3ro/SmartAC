package dev.cocot3ro.smartac

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.cocot3ro.smartac.di.KoinApp
import dev.cocot3ro.smartac.ui.theme.SmartAcAdminTheme
import org.koin.compose.KoinApplication
import org.koin.plugin.module.dsl.koinConfiguration

@Composable
fun App() {
    KoinApplication(configuration = koinConfiguration<KoinApp>()) {
        SmartAcAdminTheme {

        }
    }
}
