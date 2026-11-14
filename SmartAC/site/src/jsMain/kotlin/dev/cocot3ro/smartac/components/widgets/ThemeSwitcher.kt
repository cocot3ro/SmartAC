package dev.cocot3ro.smartac.components.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.browser.dom.ElementTarget
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.silk.components.icons.MoonIcon
import com.varabyte.kobweb.silk.components.icons.SunIcon
import com.varabyte.kobweb.silk.components.overlay.PopupPlacement
import com.varabyte.kobweb.silk.components.overlay.Tooltip
import com.varabyte.kobweb.silk.theme.colors.ColorMode

@Composable
fun ThemeSwitcher(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        var colorState by ColorMode.currentState

        IconButton(onClick = {
            colorState = colorState.opposite
        }) {
            if (colorState.isLight) MoonIcon() else SunIcon()
        }

        Tooltip(
            target = ElementTarget.PreviousSibling,
            text = "Toggle color mode", // TODO: Localize
            placement = PopupPlacement.BottomRight
        )
    }
}
