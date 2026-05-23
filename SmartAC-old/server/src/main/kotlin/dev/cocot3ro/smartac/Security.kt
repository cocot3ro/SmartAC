package dev.cocot3ro.smartac

import io.ktor.server.application.Application
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.authentication
import io.ktor.server.auth.bearer
import io.ktor.server.routing.Route
import io.ktor.server.routing.Routing

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

fun Routing.apiKeyAuth(block: Route.() -> Unit) = authenticate("api-key-auth", build = block)
