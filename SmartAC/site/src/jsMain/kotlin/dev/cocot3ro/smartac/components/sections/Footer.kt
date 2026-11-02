package dev.cocot3ro.smartac.components.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.WhiteSpace
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.setVariable
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.whiteSpace
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.navigation.UncoloredLinkVariant
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.style.vars.color.ColorVar
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.cocot3ro.smartac.SitePalette
import dev.cocot3ro.smartac.toSitePalette
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.dom.Footer
import org.jetbrains.compose.web.dom.Span

val FooterStyle = CssStyle.base {
    Modifier
        .backgroundColor(colorMode.toSitePalette().nearBackground)
        .padding(topBottom = 1.5.cssRem, leftRight = 10.percent)
}

@Composable
fun Footer(modifier: Modifier = Modifier) {
    Footer(FooterStyle.toModifier().then(modifier).toAttrs()) {
        Box(contentAlignment = Alignment.Center) {
            Span(Modifier.textAlign(TextAlign.Center).toAttrs()) {
                val sitePalette: SitePalette = ColorMode.current.toSitePalette()

                SpanText(text = "Built with ")

                Link(
                    path = "https://github.com/varabyte/kobweb",
                    text = "Kobweb",
                    modifier = Modifier.setVariable(ColorVar, sitePalette.brand.primary),
                    variant = UncoloredLinkVariant
                )

                SpanText(text = ", template designed by ")

                // Huge thanks to UI Rocket (https://ui-rocket.com) for putting this great template design together for us!
                // If you like what you see here and want help building your own site, consider checking out their services.
                Link(
                    path = "https://ui-rocket.com",
                    text = "UI Rocket",
                    modifier = Modifier.setVariable(ColorVar, sitePalette.brand.accent)
                        .whiteSpace(WhiteSpace.NoWrap),
                    variant = UncoloredLinkVariant
                )
            }
        }
    }
}
