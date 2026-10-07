package dev.cocot3ro.smartac.data.network.resources

import io.ktor.resources.Resource
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@OptIn(ExperimentalJsExport::class)
@Resource(HealthRoute.PATH)
@JsExport
class HealthRoute {
    companion object {
        const val PATH = "/health"

        fun getPath() = PATH
    }
}
