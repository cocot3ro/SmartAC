package dev.cocot3ro.smartac.data.network.routes

import io.ktor.resources.Resource
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@OptIn(ExperimentalJsExport::class)
@Resource(HealthRoute.PATH)
@JsExport
class HealthRoute {
    companion object {
        private const val PATH = "/api/health"

        fun getPath() = PATH
    }
}
