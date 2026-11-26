package dev.cocot3ro.smartac.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.text.SpanText
import dev.cocot3ro.smartac.components.layouts.PageLayoutData
import dev.cocot3ro.smartac.notifications.NotificationManager

@InitRoute
fun initHomePage(ctx: InitRouteContext) {
    ctx.data.add(PageLayoutData("Dashboard"))
}

@Suppress("ModifierRequired")
@Page
@Composable
fun HomePage() {
//    SpanText("Dashboard")
    Button(onClick = {
        NotificationManager.success("success", "success")
        NotificationManager.error("error", "error")
        NotificationManager.warning("warning", "warning")
        NotificationManager.info("info", "info")
    }) {
        SpanText("Notificación")
    }
}

/*
// Container that has a tagline and grid on desktop, and just the tagline on mobile
val HeroContainerStyle = CssStyle {
    base { Modifier.fillMaxWidth().gap(2.cssRem) }
    Breakpoint.MD { Modifier.margin { top(20.vh) } }
}

// A demo grid that appears on the homepage because it looks good
val HomeGridStyle = CssStyle.base {
    Modifier
        .gap(0.5.cssRem)
        .width(70.cssRem)
        .height(18.cssRem)
}

private val GridCellColorVar by StyleVariable<Color>()
val HomeGridCellStyle = CssStyle.base {
    Modifier
        .backgroundColor(GridCellColorVar.value())
        .boxShadow(
            blurRadius = 0.6.cssRem,
            color = GridCellColorVar.value()
        )
        .borderRadius(1.cssRem)
}

@Composable
private fun GridCell(color: Color, row: Int, column: Int, width: Int? = null, height: Int? = null) {
    Div(
        HomeGridCellStyle.toModifier()
            .setVariable(GridCellColorVar, color)
            .gridItem(row, column, width, height)
            .toAttrs()
    )
}

@InitRoute
fun initHomePage(ctx: InitRouteContext) {
    ctx.data.add(PageLayoutData("Dashboard"))
}

@Suppress("ModifierRequired")
@Page
@Composable
fun HomePage() {
    Row(HeroContainerStyle.toModifier()) {
        Box {
            val sitePalette = ColorMode.current.toSitePalette()

            Column(Modifier.gap(2.cssRem)) {
                Div(HeadlineTextStyle.toAttrs()) {
                    SpanText(
                        "Use this template as your starting point for ", Modifier.color(
                            when (ColorMode.current) {
                                ColorMode.LIGHT -> Colors.Black
                                ColorMode.DARK -> Colors.White
                            }
                        )
                    )

                    SpanText(
                        "Kobweb",
                        Modifier
                            .color(sitePalette.brand.accent)
                            // Use a shadow so this light-colored word is more visible in light mode
                            .textShadow(0.px, 0.px, blurRadius = 0.5.cssRem, color = Colors.Gray)
                    )
                }

                Div(SubheadlineTextStyle.toAttrs()) {
                    SpanText("You can read the ")
                    Link("/about", "About")
                    SpanText(" page for more information.")
                }

                val ctx = rememberPageContext()
                Button(onClick = {
                    // Change this click handler with your call-to-action behavior
                    // here. Link to an order page? Open a calendar UI? Play a movie?
                    // Up to you!
                    ctx.router.tryRoutingTo("/about")
                }, colorPalette = ColorPalettes.Blue) {
                    Text("This could be your CTA")
                }
            }
        }

        Div(
            HomeGridStyle
                .toModifier()
                .displayIfAtLeast(Breakpoint.MD)
                .grid {
                    rows { repeat(3) { size(1.fr) } }
                    columns { repeat(5) { size(1.fr) } }
                }
                .toAttrs()
        ) {
            val sitePalette = ColorMode.current.toSitePalette()
            GridCell(
                sitePalette.brand.primary,
                1,
                1,
                2,
                2
            )
            GridCell(
                ColorPalettes.Monochrome._600,
                1,
                3
            )
            GridCell(
                ColorPalettes.Monochrome._100,
                1,
                4,
                width = 2
            )
            GridCell(
                sitePalette.brand.accent,
                2,
                3,
                width = 2
            )
            GridCell(
                ColorPalettes.Monochrome._300,
                2,
                5
            )
            GridCell(
                ColorPalettes.Monochrome._800,
                3,
                1,
                width = 5
            )
        }
    }
}*/
