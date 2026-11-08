package dev.cocot3ro.smartac.components.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.silk.components.text.SpanText

@Composable
fun ThemeSwitcher(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        IconButton(onClick = {
            println("ThemeSwitcher")
        }) {
            SpanText("T")
        }
    }
}