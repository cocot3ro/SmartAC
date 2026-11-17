package dev.cocot3ro.smartac.components.layouts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.data.getValue
import com.varabyte.kobweb.core.layout.Layout
import dev.cocot3ro.smartac.components.sections.Footer
import dev.cocot3ro.smartac.components.sections.Page
import dev.cocot3ro.smartac.components.sections.SiteContainer
import dev.cocot3ro.smartac.components.sections.SiteHeader
import dev.cocot3ro.smartac.components.sections.SiteMenu
import kotlinx.browser.document
import org.jetbrains.compose.web.dom.Div

/*
val PageContentStyle = CssStyle {
    base { Modifier.fillMaxSize().padding(leftRight = 2.cssRem, top = 4.cssRem) }
    Breakpoint.MD { Modifier.maxWidth(60.cssRem) }
}

class PageLayoutData(val title: String)

@Suppress("ModifierRequired")
@Composable
@Layout(".components.layouts.RouterLayout")
fun PageLayout(
    ctx: PageContext,
    content: @Composable ColumnScope.() -> Unit
) {
    val data: PageLayoutData = ctx.data.getValue<PageLayoutData>()
    LaunchedEffect(data.title) {
        document.title = "SmartAC - ${data.title}"
    }

    Main {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .minHeight(100.vh)
                // Create a box with two rows: the main content (fills as much space as it can) and the footer (which reserves
                // space at the bottom). "min-content" means the use the height of the row, which we use for the footer.
                // Since this box is set to *at least* 100%, the footer will always appear at least on the bottom but can be
                // pushed further down if the first row grows beyond the page.
                // Grids are powerful but have a bit of a learning curve. For more info, see:
                // https://css-tricks.com/snippets/css/complete-guide-grid/
                .gridTemplateRows {
                    size(1.fr)
                    size(minContent)
                },
            contentAlignment = Alignment.Center
        ) {
            Column(
                // Isolate the content, because otherwise the absolute-positioned SVG above will render on top of it.
                // This is confusing but how browsers work. Read up on stacking contexts for more info.
                // https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_positioned_layout/Understanding_z-index/Stacking_context
                // Some people might have used z-index instead, but best practice is to avoid that if possible, because
                // as a site gets complex, Z-fighting can be a huge pain to track down.
                modifier = Modifier.fillMaxSize().gridRow(1),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                NavHeader()
                Div(attrs = PageContentStyle.toAttrs()) {
                    content()
                }
            }

            // Associate the footer with the row that will get pushed off the bottom of the page if it can't fit.
            Footer(
                modifier = Modifier.fillMaxWidth().gridRow(2)
            )
        }
    }
}
*/

class PageLayoutData(val title: String)

@Suppress("ModifierRequired")
@Composable
@Layout
fun PageLayout(
    ctx: PageContext,
    content: @Composable () -> Unit
) {
    val data: PageLayoutData = ctx.data.getValue<PageLayoutData>()
    LaunchedEffect(data.title) {
        document.title = "SmartAC - ${data.title}"
    }

    Page {
        Div {
            SiteHeader()
            SiteMenu()
        }

        SiteContainer(content = content)

        Footer()
    }
}
