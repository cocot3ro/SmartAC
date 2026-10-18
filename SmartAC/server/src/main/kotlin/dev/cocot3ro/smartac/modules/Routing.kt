package dev.cocot3ro.smartac.modules

import dev.cocot3ro.smartac.data.network.http.routing.adminRouting
import dev.cocot3ro.smartac.data.network.http.routing.apiRouting
import dev.cocot3ro.smartac.data.network.http.routing.webAppRouting
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.request.path
import io.ktor.server.routing.localPort
import io.ktor.server.routing.routing

fun Application.configureRouting() {
    install(CallLogging) {
        filter { call ->
            call.request.path().startsWith("/api/log")
        }
    }

    routing {
        localPort(8080) {
            apiRouting()
        }

        localPort(8081) {
            webAppRouting()
            adminRouting()
        }
    }
}
