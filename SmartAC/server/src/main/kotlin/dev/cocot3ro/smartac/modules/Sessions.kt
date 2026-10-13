package dev.cocot3ro.smartac.modules

import dev.cocot3ro.smartac.core.auth.AdminSession
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.sessions.SessionStorageMemory
import io.ktor.server.sessions.Sessions
import io.ktor.server.sessions.cookie

fun Application.configureSessions() {
    install(Sessions) {
        cookie<AdminSession>(
            name = "SMARTAC_ADMIN_SESSION",
            storage = SessionStorageMemory()
        ) {
            cookie.path = "/"
            cookie.httpOnly = true
            cookie.secure = true
            cookie.maxAgeInSeconds = 60 * 60 * 8 // 8 hours
            cookie.extensions["SameSite"] = "Lax"
        }
    }
}
