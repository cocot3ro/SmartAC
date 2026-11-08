package dev.cocot3ro.smartac.components.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.ColumnScope
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.PaddingScope
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.varabyte.kobweb.silk.theme.shapes.Rect
import com.varabyte.kobweb.silk.theme.shapes.clip
import dev.cocot3ro.smartac.toSitePalette
import org.jetbrains.compose.web.css.px

@Composable
fun Card(
    modifier: Modifier = Modifier,
    contentPadding: PaddingScope.() -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    val sitePalette = ColorMode.current.toSitePalette()

    Box(
        modifier = modifier.clip(shape = Rect(cornerRadius = 10.px))
            .backgroundColor(sitePalette.nearBackground),
    ) {
        Column(
            modifier = Modifier.padding(contentPadding),
            content = content
        )
    }
}
