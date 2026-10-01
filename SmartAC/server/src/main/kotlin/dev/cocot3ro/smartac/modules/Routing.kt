package dev.cocot3ro.smartac.modules

import dev.cocot3ro.smartac.data.network.http.routing.apiRouting
import dev.cocot3ro.smartac.data.network.http.routing.webAppRouting
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.calllogging.CallLogging

fun Application.configureRouting() {
    install(CallLogging)

    webAppRouting()
    apiRouting()
}
