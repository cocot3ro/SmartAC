package dev.cocot3ro.smartac.data.network.http.routing

import io.ktor.server.http.content.react
import io.ktor.server.http.content.singlePageApplication
import io.ktor.server.routing.Route

fun Route.webAppRouting() {

    singlePageApplication {
        useResources = true
        filesPath = "dist"
    }
}
