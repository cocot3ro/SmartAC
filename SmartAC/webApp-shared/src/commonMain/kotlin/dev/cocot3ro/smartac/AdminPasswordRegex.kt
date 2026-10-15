package dev.cocot3ro.smartac

import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

/**
 * Admin password must be at least 8 characters long, containing at least:
 * - one uppercase letter,
 * - one lowercase letter,
 * - one number,
 * - and one special character.
 */
@OptIn(ExperimentalJsExport::class)
@JsExport
const val ADMIN_PASSWORD_REGEX: String = """^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&\.])[A-Za-z\d@$!%*?&\.]{8,}$"""
