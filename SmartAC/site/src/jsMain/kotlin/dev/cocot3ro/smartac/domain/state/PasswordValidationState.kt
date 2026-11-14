package dev.cocot3ro.smartac.domain.state

import dev.cocot3ro.smartac.AdminPasswordValidationErrors
import dev.cocot3ro.smartac.domain.state.PasswordValidationState.Empty
import dev.cocot3ro.smartac.domain.state.PasswordValidationState.Invalid
import dev.cocot3ro.smartac.domain.state.PasswordValidationState.Valid

sealed class PasswordValidationState {
    abstract val value: String

    data object Idle : PasswordValidationState() {
        override val value: String = ""
    }

    data object Empty : PasswordValidationState() {
        override val value: String = ""
    }

    data class Valid(
        override val value: String
    ) : PasswordValidationState()

    data class Invalid(
        override val value: String,
        val errors: List<AdminPasswordValidationErrors>
    ) : PasswordValidationState()

}

fun PasswordValidationState.asInvalid(): Invalid = this as Invalid

/**
 * Check if the current state is an instance of `PasswordValidationState.Valid`
 */
fun PasswordValidationState.isValid(): Boolean = this is Valid

/**
 * Check if the current state is an instance of
 * `PasswordValidationState.Empty` or `PasswordValidationState.Empty`.
 * `PasswordValidationState.Idle` is not considered invalid
 */
fun PasswordValidationState.isInvalid(): Boolean = this is Empty || this is Invalid