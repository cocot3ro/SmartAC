package dev.cocot3ro.smartac.data.network.http.routing

import dev.cocot3ro.smartac.data.network.model.HealthResponse
import dev.cocot3ro.smartac.data.network.resources.LoginRoute
import dev.cocot3ro.smartac.data.network.resources.LogoutRoute
import io.ktor.server.application.Application
import io.ktor.server.http.content.react
import io.ktor.server.http.content.singlePageApplication
import io.ktor.server.resources.get
import io.ktor.server.resources.post
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.port
import io.ktor.server.routing.route
import io.ktor.server.routing.routing

fun Application.webAppRouting() {
    routing {
        port(8081) {
            route("/") {
                singlePageApplication {
                    useResources = true
                    react("dist")
                }
            }

            route("/api") {

                get {


                    call.respond(
                        HealthResponse(
                            setup = false,
                            version = ""
                        )
                    )
                }

//                authenticate("auth-jwt") {
                // TODO: setup authentication
                post<LoginRoute> {

                }

                get<LogoutRoute> {

                }
//                }
            }
        }
    }
}
