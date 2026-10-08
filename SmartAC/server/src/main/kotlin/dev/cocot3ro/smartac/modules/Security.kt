package dev.cocot3ro.smartac.modules

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.auth.DigestAlgorithm
import io.ktor.server.application.Application
import io.ktor.server.auth.DigestCredential
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.authentication
import io.ktor.server.auth.digest
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.routing.Route
import io.ktor.server.routing.Routing
import org.koin.ktor.ext.getProperty

val userHashes: Map<String, String> = mapOf(
    "019f0df5-73e1-7eb1-a926-cfde8473263b" to "b10941c251e1c5eb9fff72cba21fefa12304ad9331221ad76c6f3378c1dabb11"
)

fun Application.configureSecurity() {
    authentication {

        jwt("auth-jwt") {
            val jwtSecret: String = this@configureSecurity.getProperty<String>("JWT_SECRET")!!
            val jwtIssuer: String? = this@configureSecurity.getProperty<String>("JWT_ISSUER")
            val jwtAudience: String? = this@configureSecurity.getProperty<String>("JWT_AUDIENCE")

            realm = "SmartAC API"

            verifier(
                JWT
                    .require(Algorithm.HMAC256(jwtSecret))
                    .withAudience(jwtAudience)
                    .withIssuer(jwtIssuer)
                    .build()
            )

            validate { credential ->
                if (credential.payload.getClaim("id").asString().isNotEmpty()) {
                    UserIdPrincipal(credential.payload.getClaim("id").asString())
                } else {
                    null
                }
            }
        }

        digest("auth-digest") {
            realm = "SmartAC API"

            // SHA_512_256 is not supported by ESP32
            algorithms = listOf(DigestAlgorithm.SHA_256)

            digestProvider { userName: String, realm: String ->
                userHashes[userName]?.hexToByteArray()

//                userPasswords[userName]?.let { password ->
//                    computeHash(userName, realm, password, algorithm)
//                }
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

fun Routing.digestAuth(build: Route.() -> Unit) = authenticate("auth-digest", build = build)

fun Routing.jwtAuth(build: Route.() -> Unit) = authenticate("auth-jwt", build = build)
