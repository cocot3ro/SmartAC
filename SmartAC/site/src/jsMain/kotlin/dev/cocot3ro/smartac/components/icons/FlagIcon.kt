package dev.cocot3ro.smartac.components.icons

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.classNames
import com.varabyte.kobweb.compose.ui.toAttrs
import org.jetbrains.compose.web.dom.Span

@Composable
fun FlagIcon(
    languageCode: String,
    modifier: Modifier = Modifier
) {

    when (languageCode) {
        "en" -> LanguageIcon(attrs = modifier.toAttrs())

        else -> Span(
            attrs = modifier
                .classNames("flag", "flag-xs", "flag-country-$languageCode")
                .toAttrs()
        )
    }
}
