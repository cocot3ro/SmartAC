package dev.cocot3ro.smartac

/**
 * Admin password must be at least 8 characters long, containing at least:
 * - one uppercase letter,
 * - one lowercase letter,
 * - one number,
 * - and one special character.
 */
const val ADMIN_PASSWORD_REGEX: String =
    """^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$#!%*?&^.])[A-Za-z\d@$#!%*?&^.]{8,}$"""
