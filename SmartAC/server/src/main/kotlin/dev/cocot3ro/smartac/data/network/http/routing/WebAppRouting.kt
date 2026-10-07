package dev.cocot3ro.smartac.data.network.http.routing

import dev.cocot3ro.smartac.data.network.model.HealthResponse
import dev.cocot3ro.smartac.data.network.model.VersionModel
import dev.cocot3ro.smartac.data.network.resources.HealthRoute
import dev.cocot3ro.smartac.data.network.resources.LoginRoute
import dev.cocot3ro.smartac.data.network.resources.LogoutRoute
import dev.cocot3ro.smartac.domain.usecase.IsSetupUseCase
import io.ktor.server.application.Application
import io.ktor.server.http.content.react
import io.ktor.server.http.content.singlePageApplication
import io.ktor.server.resources.get
import io.ktor.server.resources.post
import io.ktor.server.response.respond
import io.ktor.server.routing.port
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject

fun Application.webAppRouting() {
    routing {
        port(8081) {
            singlePageApplication {
                useResources = true
                react("dist")
            }

            route("/api") {

                get<HealthRoute> { _ ->
                    val isSetup by inject<IsSetupUseCase>()

                    call.respond(
                        HealthResponse(
                            setup = isSetup(),
                            // TODO: Replace with the real version
                            version = VersionModel(0, 0, 1)
                        )
                    )
                }

                // TODO: setup authentication
//                authenticate("auth-jwt") {
                post<LoginRoute> {

                }

                get<LogoutRoute> {

                }
//                }
            }
        }
    }
}
