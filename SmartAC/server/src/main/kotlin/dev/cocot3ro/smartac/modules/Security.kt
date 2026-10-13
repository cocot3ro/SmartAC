package dev.cocot3ro.smartac.modules

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import dev.cocot3ro.smartac.core.auth.AdminSession
import dev.cocot3ro.smartac.core.auth.JwtSecretProvider
import dev.cocot3ro.smartac.data.database.repository.DeviceUserRepository
import io.ktor.http.HttpStatusCode
import io.ktor.http.auth.DigestAlgorithm
import io.ktor.server.application.Application
import io.ktor.server.auth.DigestCredential
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.authentication
import io.ktor.server.auth.digest
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.auth.session
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import org.koin.ktor.ext.inject
import kotlin.uuid.Uuid

fun Application.configureSecurity() {
    authentication {

        session<AdminSession>("auth-session") {
            validate { session: AdminSession ->
                session.takeIf { session.authenticated }
            }

            challenge {
                call.respond(HttpStatusCode.Unauthorized)
            }
        }

        jwt("auth-jwt") {
            val jwtSecretProvider by this@configureSecurity.inject<JwtSecretProvider>()

            val jwtSecret: String = jwtSecretProvider.loadOrCreate()

            realm = "SmartAC API"

            verifier(
                JWT
                    .require(Algorithm.HMAC256(jwtSecret))
                    .withAudience("smartac-api")
                    .withIssuer("smartac")
                    .build()
            )

            validate { credential ->
                if (credential.payload.getClaim("id").asString().isEmpty()) {
                    return@validate null
                }

                UserIdPrincipal(credential.payload.getClaim("id").asString())
            }
        }

        digest("auth-digest") {
            realm = "SmartAC API"

            // SHA_512_256 is not supported by ESP32
            algorithms = listOf(DigestAlgorithm.SHA_256)

            digestProvider { userId: String, _: String, _: DigestAlgorithm ->
                val deviceUserRepository by this@configureSecurity.inject<DeviceUserRepository>()

                val user = deviceUserRepository.getById(Uuid.parse(userId))

                user?.passwordHash
            }

            validate { credentials: DigestCredential ->
                if (credentials.userName.isBlank()) {
                    return@validate null
                }

                UserIdPrincipal(credentials.userName)
            }
        }
    }
}

fun Route.digestAuth(build: Route.() -> Unit) = authenticate("auth-digest", build = build)

fun Route.adminSessionAuth(build: Route.() -> Unit) = authenticate("auth-session", build = build)

fun Route.jwtAuth(build: Route.() -> Unit) = authenticate("auth-jwt", build = build)
