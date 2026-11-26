package dev.cocot3ro.smartac.data.network.http.routing

import io.ktor.server.http.content.singlePageApplication
import io.ktor.server.http.content.staticFiles
import io.ktor.server.routing.Route
import java.io.File

fun Route.webAppRouting() {

    singlePageApplication {
        useResources = true
        filesPath = "dist"
        defaultPage = "index.html"
    }

//    staticFiles("/", File("dist")) {
//        enableAutoHeadResponse()
//        extensions("html")
//        default("index.html")
//    }
}
