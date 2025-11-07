package dev.cocot3ro.smartac.network.http.routing

import dev.cocot3ro.smartac.network.NetworkRepository
import dev.cocot3ro.smartac.network.http.resources.Api
import io.ktor.server.application.Application
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.routing
import io.ktor.server.websocket.webSocket
import org.koin.ktor.ext.inject

fun Application.apiRouting() {

    val repository by inject<NetworkRepository>()

    routing {
        authenticate("api-key-auth") {

            webSocket(path = Api.Ac.Status.getRoute()) {

            }
        }
    }
}