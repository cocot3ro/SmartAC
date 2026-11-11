package dev.cocot3ro.smartac.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.role
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.classNames
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.breakpoint.displayIfAtLeast
import dev.cocot3ro.smartac.components.widgets.LocalePicker
import dev.cocot3ro.smartac.components.widgets.Page
import dev.cocot3ro.smartac.components.widgets.ThemeSwitcher
import dev.cocot3ro.smartac.context.LocalHealthContext
import dev.cocot3ro.smartac.context.asHealthy
import dev.cocot3ro.smartac.domain.state.HealthState
import libres.resources.Res
import org.jetbrains.compose.web.attributes.AutoComplete
import org.jetbrains.compose.web.attributes.autoComplete
import org.jetbrains.compose.web.attributes.name
import org.jetbrains.compose.web.attributes.onSubmit
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Footer
import org.jetbrains.compose.web.dom.Form
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.Header
import org.jetbrains.compose.web.dom.Label
import org.jetbrains.compose.web.dom.Main
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.PasswordInput
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.events.SyntheticSubmitEvent

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
                attrs = {
                    classes("sr-only")
                }
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

            Main(attrs = {
                classes("card", "card-md")
            }) {

                Header(attrs = {
                    classes("card-body", "text-center", "py-3")
                }) {
                    H2(attrs = {
                        classes("mt-4")
                    }) {
                        Text(value = Res.string.welcome)
                    }
                    P(attrs = {
                        classes("text-secondary")
                    }) {
                        // TODO: Localize
                        Text(value = "Comienza creando tu cuenta de administrador")
                    }
                }

                Div(attrs = {
                    classes("hr")
                })

                Form(attrs = {
                    classes("card-body", "py-3")

                    onSubmit { ev: SyntheticSubmitEvent ->
                        ev.preventDefault()

                        // TODO: proceed with setup
                    }
                }) {
                    Div(attrs = {
                        role("region")
                        classes("mb-3")
                    }) {
                        Label(
                            forId = "password",
                            attrs = {
                                classes("form-label", "required")
                            }
                        ) {
                            SpanText(Res.string.password)
                        }

                        PasswordInput(attrs = {
                            id("password")
                            name("password")
                            classes("form-control")
                            placeholder(Res.string.password)
                            autoComplete(AutoComplete.newPassword)
                        })
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
