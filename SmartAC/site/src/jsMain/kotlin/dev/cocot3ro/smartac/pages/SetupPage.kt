package dev.cocot3ro.smartac.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.ariaInvalid
import com.varabyte.kobweb.compose.css.role
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.classNames
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.breakpoint.displayIfAtLeast
import dev.cocot3ro.smartac.ADMIN_PASSWORD_REGEX
import dev.cocot3ro.smartac.LOWERCASE
import dev.cocot3ro.smartac.SYMBOLS
import dev.cocot3ro.smartac.UPPERCASE
import dev.cocot3ro.smartac.components.widgets.LocalePicker
import dev.cocot3ro.smartac.components.widgets.Page
import dev.cocot3ro.smartac.components.widgets.ThemeSwitcher
import dev.cocot3ro.smartac.context.LocalHealthContext
import dev.cocot3ro.smartac.context.asHealthy
import dev.cocot3ro.smartac.domain.state.HealthState
import libres.resources.Res
import org.jetbrains.compose.web.attributes.AutoComplete
import org.jetbrains.compose.web.attributes.ButtonType
import org.jetbrains.compose.web.attributes.autoComplete
import org.jetbrains.compose.web.attributes.name
import org.jetbrains.compose.web.attributes.onSubmit
import org.jetbrains.compose.web.attributes.pattern
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.attributes.type
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Footer
import org.jetbrains.compose.web.dom.Form
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.Header
import org.jetbrains.compose.web.dom.Label
import org.jetbrains.compose.web.dom.Li
import org.jetbrains.compose.web.dom.Main
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.PasswordInput
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.Ul
import org.jetbrains.compose.web.events.SyntheticInputEvent
import org.jetbrains.compose.web.events.SyntheticSubmitEvent
import org.w3c.dom.HTMLInputElement

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
                        SpanText(text = "Comienza creando tu cuenta de administrador")
                    }
                }

                Div(attrs = {
                    classes("hr")
                })

                var password by remember { mutableStateOf("") }

                Form(attrs = {
                    classes("card-body", "py-3")

                    onSubmit { ev: SyntheticSubmitEvent ->
                        ev.preventDefault()

                        // TODO: proceed with setup

                        // If all goes well, clean the password
                        password = ""
                    }
                }) {

                    //FIXME: Input display valid status before starting writing

                    Div(attrs = {
                        role("region")
                        classes("form-floating", "mb-2")
                    }) {

                        val errors = remember { mutableStateListOf<String>() }

                        val passwordRegex: Regex by remember { lazy { ADMIN_PASSWORD_REGEX.toRegex() } }

                        PasswordInput(
                            value = password,
                            attrs = {
                                id("password")
                                name("password")
                                classes("form-control")
                                if (errors.isNotEmpty()) {
                                    classes("is-invalid")
                                    ariaInvalid(true)
                                } else {
                                    classes("is-valid")
                                    ariaInvalid(false)
                                }
                                placeholder(Res.string.password)
                                autoComplete(AutoComplete.newPassword)
                                pattern(ADMIN_PASSWORD_REGEX)
                                // TODO: Aria error message
                                onInput { ev: SyntheticInputEvent<String, HTMLInputElement> ->
                                    password = ev.value

                                    // TODO: Should use a validatePassword(password) function. Use PasswordState.{Idle, Valid, Invalid}
                                    errors.clear()
                                    if (password.isEmpty()) {
                                        errors.add("Este campo es obligatorio")
                                    } else if (!passwordRegex.matches(password)) {
                                        if (password.length < 8) {
                                            errors.add("La contraseña debe tener al menos 8 caracteres")
                                        }

                                        if (!password.contains(Regex("[$UPPERCASE]"))) {
                                            errors.add("Debe contener al menos una mayúscula")
                                        }

                                        if (!password.contains(Regex("[$LOWERCASE]"))) {
                                            errors.add("Debe contener al menos una minúscula")
                                        }

                                        if (!password.contains("""\d""".toRegex())) {
                                            errors.add("Debe contener al menos un número")
                                        }

                                        if (!password.contains(Regex("[$SYMBOLS]"))) {
                                            errors.add("Debe contener al menos uno de los siguientes símbolos: $SYMBOLS")
                                        }
                                    }
                                }
                            }
                        )

                        Label(
                            forId = "password"
                        ) {
                            SpanText(Res.string.password)
                        }

                        if (errors.isNotEmpty()) {
                            Ul(attrs = {
                                classes("invalid-feedback")
                            }) {
                                errors.forEach { errorMessage: String ->
                                    Li {
                                        SpanText(text = errorMessage)
                                    }
                                }
                            }
                        }

                        if (errors.isEmpty()) {
                            SpanText(
                                modifier = Modifier.classNames("valid-feedback"),
                                text = "Valid password"
                            )
                        }
                    }

                    Div(attrs = {
                        classes("form-footer")
                    }) {
                        Button(attrs = {
                            type(ButtonType.Submit)
                            classes("btn", "btn-azure", "w-100")
                        }) {
                            // TODO: Localize
                            SpanText(text = "Guardar")
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
