package dev.cocot3ro.smartac.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.classNames
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.right
import com.varabyte.kobweb.compose.ui.modifiers.top
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.breakpoint.displayIfAtLeast
import dev.cocot3ro.smartac.components.widgets.Card
import dev.cocot3ro.smartac.components.widgets.LocalePicker
import dev.cocot3ro.smartac.components.widgets.Page
import dev.cocot3ro.smartac.components.widgets.ThemeSwitcher
import dev.cocot3ro.smartac.context.LocalHealthContext
import dev.cocot3ro.smartac.context.asHealthy
import dev.cocot3ro.smartac.domain.state.HealthState
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.css.vw
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Footer
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.Header
import org.jetbrains.compose.web.dom.Main
import org.jetbrains.compose.web.dom.Text

@Suppress("ModifierRequired", "MultipleContentEmitters", "ExplicitDependencies")
@Composable
fun SetupPage() {
    Page(modifier = Modifier.classNames("page", "page-center")) {
        Div(
            attrs = {
                classes("container", "container-tight", "py-4")
            }
        ) {
            H1(
                attrs = Modifier
                    .position(Position.Relative)
                    .top(100.vh)
                    .right(100.vw)
                    .toAttrs()
            ) {
                Text("SmartAC")
            }

            Header(
                attrs = {
                    classes(
                        "d-flex", "justify-content-between",
                        "flex-row-reverse", "align-items-center", "mb-4", "pe-3"
                    )
                }
            ) {
                Row(modifier = Modifier.classNames("d-flex", "align-items-center", "gap-1")) {
                    LocalePicker()
                    ThemeSwitcher()
                }

                Image(
                    src = "/kobweb-logo.png",
                    description = "Kobweb Logo",
                    modifier = Modifier.displayIfAtLeast(Breakpoint.MD).classNames("d-block")
                )
            }

            Main {
                Card {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(all = 1.cssRem),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        H2 {
                            Text(value = "¡Bienvenido!")
                        }
                    }
                }
            }

            Footer(
                attrs = {
                    classes("text-center", "text-secondary", "mt-3")
                }
            ) {
                val health: HealthState.Healthy = LocalHealthContext.current.asHealthy()

                SpanText("v${health.data.version}")
            }
        }
    }
}
