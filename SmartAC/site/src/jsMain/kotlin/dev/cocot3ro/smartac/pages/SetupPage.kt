package dev.cocot3ro.smartac.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.AlignItems
import com.varabyte.kobweb.compose.css.JustifyContent
import com.varabyte.kobweb.compose.css.ariaInvalid
import com.varabyte.kobweb.compose.css.role
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.classNames
import com.varabyte.kobweb.compose.ui.modifiers.justifyContent
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.breakpoint.displayIfAtLeast
import com.varabyte.kobweb.silk.style.breakpoint.displayUntil
import com.varabyte.kobweb.silk.style.toAttrs
import com.varabyte.kobweb.silk.style.until
import dev.cocot3ro.smartac.ADMIN_PASSWORD_PATTERN
import dev.cocot3ro.smartac.AdminPasswordValidationErrors.INSUFFICIENT_LENGTH
import dev.cocot3ro.smartac.AdminPasswordValidationErrors.MISSING_LOWERCASE
import dev.cocot3ro.smartac.AdminPasswordValidationErrors.MISSING_NUMBER
import dev.cocot3ro.smartac.AdminPasswordValidationErrors.MISSING_SYMBOL
import dev.cocot3ro.smartac.AdminPasswordValidationErrors.MISSING_UPPERCASE
import dev.cocot3ro.smartac.PASSWORD_MIN_LENGTH
import dev.cocot3ro.smartac.SYMBOLS_PATTERN
import dev.cocot3ro.smartac.components.widgets.LocalePicker
import dev.cocot3ro.smartac.components.widgets.Page
import dev.cocot3ro.smartac.components.widgets.ThemeSwitcher
import dev.cocot3ro.smartac.context.AuthContext
import dev.cocot3ro.smartac.context.HealthContext
import dev.cocot3ro.smartac.context.LocalAuthContext
import dev.cocot3ro.smartac.context.LocalHealthContext
import dev.cocot3ro.smartac.context.asHealthy
import dev.cocot3ro.smartac.data.network.NetworkRepository
import dev.cocot3ro.smartac.domain.state.HealthState
import dev.cocot3ro.smartac.domain.state.PasswordValidationState
import dev.cocot3ro.smartac.domain.state.asInvalid
import dev.cocot3ro.smartac.domain.state.isInvalid
import dev.cocot3ro.smartac.domain.state.isValid
import dev.cocot3ro.smartac.validatePassword
import kotlinx.coroutines.launch
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
import org.koin.compose.koinInject
import org.w3c.dom.HTMLInputElement

val HeaderMenuStyles = CssStyle {
    until(Breakpoint.SM) {
        Modifier.justifyContent(JustifyContent.Center)
    }

    Breakpoint.SM {
        Modifier.alignItems(AlignItems.Center)
            .justifyContent(JustifyContent.SpaceBetween)
    }
}

@Suppress("ModifierRequired", "MultipleContentEmitters", "ExplicitDependencies")
@Composable
fun SetupPage() {

    val healthContext: HealthContext = LocalHealthContext.current
    val authContext: AuthContext = LocalAuthContext.current
    val networkRepository: NetworkRepository = koinInject()

    Page(modifier = Modifier.classNames("page", "page-center")) {
        Div(attrs = {
            classes("container", "container-tight", "py-4")
        }) {
            H1(attrs = {
                classes("sr-only")
            }) {
                Text("SmartAC")
            }

            Header(attrs = HeaderMenuStyles.toAttrs {
                classes(
                    "mb-4",
                    "pe-3",
                    "d-flex",
                    "flex-row"
                )
            }) {
                Image(
                    src = "/kobweb-logo.png",
                    description = "Kobweb Logo",
                    modifier = Modifier.classNames("d-block")
                )

                Row(
                    modifier = Modifier.displayIfAtLeast(Breakpoint.SM)
                        .classNames("d-flex", "align-items-center", "gap-1")
                ) {
                    LocalePicker()
                    ThemeSwitcher()
                }
            }

            Main(attrs = {
                classes("card", "card-md")
            }) {

                Header(attrs = {
                    classes("card-body", "text-center", "py-3")
                }) {
                    Box {
                        Row(
                            modifier = Modifier.displayUntil(Breakpoint.SM)
                                .align(Alignment.TopEnd)
                                .classNames("d-flex", "align-items-center", "gap-1")
                        ) {
                            LocalePicker()
                            ThemeSwitcher()
                        }

                        Column(
                            modifier = Modifier.align(Alignment.TopCenter)
                                .alignItems(AlignItems.Center)
                        ) {
                            H2(attrs = {
                                classes("mt-6")
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
                    }
                }

                Div(attrs = {
                    classes("hr")
                })

                var passwordState: PasswordValidationState by remember {
                    mutableStateOf(PasswordValidationState.Idle)
                }

                val coroutineScope = rememberCoroutineScope()

                Form(attrs = {
                    classes("card-body", "py-3")

                    onSubmit { ev: SyntheticSubmitEvent ->
                        ev.preventDefault()

                        coroutineScope.launch {

                            if (passwordState is PasswordValidationState.Idle) {
                                passwordState = PasswordValidationState.Empty
                                return@launch
                            }

                            if (passwordState !is PasswordValidationState.Valid) {
                                return@launch
                            }

                            networkRepository.setup(passwordState.value)
                                .onSuccess {
                                    if (it) {
                                        authContext.login(passwordState.value)
                                        healthContext.refetchHealth()
                                        passwordState = PasswordValidationState.Idle
                                    }
                                }
                        }
                    }
                }) {

                    Div(attrs = {
                        role("region")
                        classes("form-floating", "mb-2")
                    }) {

                        PasswordInput(
                            value = passwordState.value,
                            attrs = {
                                id("password")
                                name("password")

                                classes(buildList {
                                    add("form-control")
                                    if (passwordState.isValid()) {
                                        add("is-valid")
                                    } else if (passwordState.isInvalid()) {
                                        add("is-invalid")
                                    }
                                })
                                ariaInvalid(passwordState.isInvalid())

                                placeholder(Res.string.password)
                                autoComplete(AutoComplete.newPassword)
                                pattern(ADMIN_PASSWORD_PATTERN)

                                // TODO: Aria error message

                                onFocusOut {
                                    if (passwordState is PasswordValidationState.Idle) {
                                        passwordState = PasswordValidationState.Empty
                                    }
                                }

                                onInput { ev: SyntheticInputEvent<String, HTMLInputElement> ->
                                    val password: String = ev.value

                                    if (password.isEmpty()) {
                                        passwordState = PasswordValidationState.Empty
                                    } else {
                                        val validationErrors = validatePassword(password)

                                        passwordState = if (validationErrors.isNotEmpty()) {
                                            PasswordValidationState.Invalid(
                                                password,
                                                validationErrors
                                            )
                                        } else {
                                            PasswordValidationState.Valid(password)
                                        }
                                    }
                                }
                            }
                        )

                        Label(forId = "password") {
                            SpanText(Res.string.password)
                        }

                        Div(attrs = {
                            if (passwordState.isValid()) {
                                classes("valid-feedback")
                            } else if (passwordState.isInvalid()) {
                                classes("invalid-feedback")
                            }
                        }) {
                            when (passwordState) {
                                PasswordValidationState.Idle -> Unit

                                PasswordValidationState.Empty -> SpanText(
                                    text = "Campo requerido" // TODO: Localize
                                )

                                is PasswordValidationState.Invalid -> Div {
                                    SpanText(text = "La contraseña debe tener al menos:")

                                    Ul {
                                        passwordState.asInvalid().errors.forEach { error ->
                                            Li {
                                                SpanText(
                                                    text = when (error) {
                                                        // TODO: Localize
                                                        INSUFFICIENT_LENGTH -> "$PASSWORD_MIN_LENGTH caracteres."
                                                        // TODO: Localize
                                                        MISSING_LOWERCASE -> "Una letra minúscula."
                                                        // TODO: Localize
                                                        MISSING_UPPERCASE -> "Una letra mayúscula."
                                                        // TODO: Localize
                                                        MISSING_NUMBER -> "Un número."
                                                        // TODO: Localize
                                                        MISSING_SYMBOL -> "Un símbolo ($SYMBOLS_PATTERN)."
                                                    }
                                                )
                                            }
                                        }
                                    }
                                }

                                is PasswordValidationState.Valid -> SpanText(
                                    text = "Contraseña valida" // TODO: Localize
                                )
                            }
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

            Footer(attrs = {
                classes("text-center", "text-secondary", "mt-3")
            }) {
                val health: HealthState.Healthy = LocalHealthContext.current.asHealthy()

                SpanText(text = "v${health.data.version}")
            }
        }
    }
}
