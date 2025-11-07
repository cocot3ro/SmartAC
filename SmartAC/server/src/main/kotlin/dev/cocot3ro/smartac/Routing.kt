package dev.cocot3ro.smartac

import dev.cocot3ro.smartac.network.http.routing.apiRouting
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.resources.Resources

fun Application.configureRouting() {
    install(Resources)
    apiRouting()
}