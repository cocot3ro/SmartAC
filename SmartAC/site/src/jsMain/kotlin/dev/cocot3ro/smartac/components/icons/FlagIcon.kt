package dev.cocot3ro.smartac.components.icons

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.classNames
import com.varabyte.kobweb.compose.ui.toAttrs
import org.jetbrains.compose.web.dom.I

@Composable
fun FlagIcon(
    countryCode: String,
    modifier: Modifier = Modifier
) {
    I(
        attrs = modifier
            .classNames("flag", "flag-xs", "flag-country-$countryCode")
            .toAttrs()
    )
}
