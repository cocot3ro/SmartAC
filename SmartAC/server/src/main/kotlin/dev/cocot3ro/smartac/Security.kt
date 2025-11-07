package dev.cocot3ro.smartac

import io.ktor.server.application.Application
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.authentication
import io.ktor.server.auth.bearer

fun Application.configureSecurity() {
    authentication {
        bearer("api-key-auth") {
            authenticate { tokenCredential ->
                val apiKey =
                    this@configureSecurity.environment.config.property("api.key").getString()
                UserIdPrincipal("apiKeyUser").takeIf { tokenCredential.token == apiKey }
            }
        }
    }
}
