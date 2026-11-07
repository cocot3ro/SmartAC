package dev.cocot3ro.smartac.plugins

import dev.cocot3ro.smartac.utils.IsSetup
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.PipelineCall
import io.ktor.server.application.createRouteScopedPlugin
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.RouteSelector
import io.ktor.server.routing.RouteSelectorEvaluation
import io.ktor.server.routing.RoutingResolveContext
import org.koin.ktor.ext.inject

val SetupCheck = createRouteScopedPlugin(name = "SetupCheck") {

    onCall { call: PipelineCall ->

        val isSetup by call.inject<IsSetup>()

        if (!isSetup()) {
            call.respond(HttpStatusCode.ServiceUnavailable)
        }
    }
}

fun Route.setupCheck(
    build: Route.() -> Unit
) {
    val route = createChild(object : RouteSelector() {
        override suspend fun evaluate(context: RoutingResolveContext, segmentIndex: Int) =
            RouteSelectorEvaluation.Transparent
    })

    route.install(SetupCheck)
    route.build()
}
