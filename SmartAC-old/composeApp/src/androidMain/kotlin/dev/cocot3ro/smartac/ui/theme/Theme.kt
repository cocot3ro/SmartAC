package dev.cocot3ro.smartac.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Suppress("ModifierRequired")
@Composable
fun SmartAcTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = dynamicDarkColorScheme(LocalContext.current),
        content = content
    )
}