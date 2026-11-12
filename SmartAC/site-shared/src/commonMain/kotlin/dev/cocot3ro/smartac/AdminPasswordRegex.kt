package dev.cocot3ro.smartac

const val UPPERCASE = "A-ZÁÉÍÓÚÑÄËÏÖÜ"
const val LOWERCASE = "a-záéíóúñäëïöü"
const val SYMBOLS = """@$#!%*?&^."""

/**
 * Admin password must be at least 8 characters long, containing at least:
 * - one uppercase letter,
 * - one lowercase letter,
 * - one number,
 * - and one special character.
 */
const val ADMIN_PASSWORD_REGEX: String =
    """^(?=.*[$LOWERCASE])""" +
            """(?=.*[$UPPERCASE])""" +
            """(?=.*\d)""" +
            """(?=.*[$SYMBOLS])""" +
            """[$UPPERCASE$LOWERCASE\d$SYMBOLS]{8,}$"""
