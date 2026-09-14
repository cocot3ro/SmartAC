package dev.cocot3ro.smartac.modules

import io.ktor.http.auth.DigestAlgorithm
import io.ktor.http.auth.toDigester
import io.ktor.server.application.Application
import io.ktor.server.auth.DigestCredential
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.authentication
import io.ktor.server.auth.digest
import io.ktor.server.routing.Route
import io.ktor.server.routing.Routing

val userHashes: Map<String, String> = mapOf(
    "019f0df5-73e1-7eb1-a926-cfde8473263b" to "b10941c251e1c5eb9fff72cba21fefa12304ad9331221ad76c6f3378c1dabb11"
)

fun Application.configureSecurity() {
    authentication {
        digest("auth-digest") {
            realm = "SmartAC API"

            algorithms = listOf(DigestAlgorithm.SHA_256) // SHA_512_256 is not supported by ESP32

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

private fun computeHash(
    userName: String,
    realm: String,
    password: String,
    algorithm: DigestAlgorithm = DigestAlgorithm.SHA_256
): ByteArray {
    return algorithm.toDigester().digest("$userName:$realm:$password".toByteArray(Charsets.UTF_8))
}
