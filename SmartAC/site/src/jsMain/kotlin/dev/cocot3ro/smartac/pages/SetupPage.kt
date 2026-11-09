package dev.cocot3ro.smartac.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.classNames
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.text.SpanText
import dev.cocot3ro.smartac.components.widgets.Card
import dev.cocot3ro.smartac.components.widgets.LocalePicker
import dev.cocot3ro.smartac.components.widgets.Page
import dev.cocot3ro.smartac.components.widgets.ThemeSwitcher
import dev.cocot3ro.smartac.context.LocalHealthContext
import dev.cocot3ro.smartac.domain.state.HealthState
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Footer
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.Header
import org.jetbrains.compose.web.dom.Main

@Suppress("ModifierRequired", "MultipleContentEmitters", "ExplicitDependencies")
@Composable
fun SetupPage() {
    Page(modifier = Modifier.classNames("page", "page-center")) {
        Div(
            attrs = Modifier
                .classNames("container", "container-tight", "py-4")
                .toAttrs(),
        ) {
            Header(
                attrs = Modifier
                    .classNames(
                        "d-flex",
                        "justify-content-between",
                        "align-items-center",
                        "mb-4", "ps-4", "pe-3"
                    )
                    .toAttrs()
            ) {
                Image(
                    "/kobweb-logo.png",
                    "Kobweb Logo",
                    Modifier.classNames("d-block")
                )

                Row(modifier = Modifier.classNames("d-flex", "align-items-center", "gap-1")) {
                    LocalePicker()
                    ThemeSwitcher()
                }
            }

            Main {
                Card {
                    Column(
                        modifier = Modifier.padding(all = 1.cssRem),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        H2 {
                            SpanText(text = "¡Bienvenido!")
                        }
                    }
                }
            }

            Footer(
                attrs = Modifier
                    .classNames("text-center", "text-secondary", "mt-3")
                    .toAttrs()
            ) {
                val health = LocalHealthContext.current.healthState as HealthState.Healthy

                SpanText("v${health.data.version}")
            }
        }
    }
}
