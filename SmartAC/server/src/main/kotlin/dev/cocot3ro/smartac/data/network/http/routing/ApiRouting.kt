package dev.cocot3ro.smartac.data.network.http.routing

import dev.cocot3ro.smartac.modules.digestAuth
import io.ktor.server.http.content.staticResources
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route

fun Route.apiRouting() {
    route("/api/v1") {
        digestAuth {
            get(path = "/ota/check") {
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
