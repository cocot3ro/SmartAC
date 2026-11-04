package dev.cocot3ro.smartac.pages.login

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.layout.NoLayout
import org.jetbrains.compose.web.dom.Main

@Suppress("ModifierRequired")
@Composable
@NoLayout
@Page
fun LoginPage() {
    Main {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            
        }
    }
}
