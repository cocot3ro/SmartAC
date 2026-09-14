package dev.cocot3ro.smartac.modules

import dev.cocot3ro.smartac.User
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.http.content.staticResources
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun Application.configureRouting() {
    install(CallLogging)

    routing {

        get("/api/user") {
            call.respond(User("Manolito", "Gafotas"))
        }

        digestAuth {
            get(path = "/api/v1/ota/check") {
                call.respond(
                    mapOf(
                        "version" to "0.0.1",
                        "download_url" to "https://test.smartac.cocot3ro.freeddns.org/fw/firmware.bin"
                    )
                )
            }

            staticResources(remotePath = "/fw", basePackage = "static")
        }
    }
}
