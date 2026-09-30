package dev.cocot3ro.smartac.data.network.http.webapp.routing

import dev.cocot3ro.smartac.data.network.resources.Foo
import io.ktor.server.application.Application
import io.ktor.server.http.content.react
import io.ktor.server.http.content.singlePageApplication
import io.ktor.server.resources.get
import io.ktor.server.routing.port
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import io.ktor.server.resources.post

fun Application.webAppRouting() {
    routing {
        port(8081) {
            route("/") {
                singlePageApplication {
                    useResources = true
                    react("dist")
                }
            }

//            get<Foo> { }
//            post<Foo> { }
        }
    }
}
