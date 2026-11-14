package dev.cocot3ro.smartac

const val PASSWORD_MIN_LENGTH = 8
const val UPPERCASE_PATTERN = "A-ZÁÉÍÓÚÑÄËÏÖÜ"
const val LOWERCASE_PATTERN = "a-záéíóúñäëïöü"
const val SYMBOLS_PATTERN = """@\$#!%*?&\^."""

/**
 * Admin password must be at least 8 characters long, containing at least:
 * - one uppercase letter,
 * - one lowercase letter,
 * - one number,
 * - and one special character.
 */
const val ADMIN_PASSWORD_PATTERN: String =
    """^(?=.*[$LOWERCASE_PATTERN])""" +
            """(?=.*[$UPPERCASE_PATTERN])""" +
            """(?=.*\d)""" +
            """(?=.*[$SYMBOLS_PATTERN])""" +
            """[$UPPERCASE_PATTERN$LOWERCASE_PATTERN\d$SYMBOLS_PATTERN]{$PASSWORD_MIN_LENGTH,}$"""

enum class AdminPasswordValidationErrors {
    INSUFFICIENT_LENGTH,
    MISSING_LOWERCASE,
    MISSING_UPPERCASE,
    MISSING_NUMBER,
    MISSING_SYMBOL
}

fun validatePassword(password: String): List<AdminPasswordValidationErrors> = buildList {
    if (!ADMIN_PASSWORD_PATTERN.toRegex().matches(password)) {
        if (password.length < PASSWORD_MIN_LENGTH) {
            add(AdminPasswordValidationErrors.INSUFFICIENT_LENGTH)
        }

        if (!password.contains(Regex("[$UPPERCASE_PATTERN]"))) {
            add(AdminPasswordValidationErrors.MISSING_UPPERCASE)
        }

        if (!password.contains(Regex("[$LOWERCASE_PATTERN]"))) {
            add(AdminPasswordValidationErrors.MISSING_LOWERCASE)
        }

        if (!password.contains("""\d""".toRegex())) {
            add(AdminPasswordValidationErrors.MISSING_NUMBER)
        }

        if (!password.contains(Regex("[$SYMBOLS_PATTERN]"))) {
            add(AdminPasswordValidationErrors.MISSING_SYMBOL)
        }
    }
}
