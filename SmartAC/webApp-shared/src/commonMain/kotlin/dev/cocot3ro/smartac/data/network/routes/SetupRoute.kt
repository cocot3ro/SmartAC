package dev.cocot3ro.smartac.data.network.routes

import io.ktor.resources.Resource
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@OptIn(ExperimentalJsExport::class)
@Resource(SetupRoute.PATH)
@JsExport
class SetupRoute {

    companion object {
        private const val PATH = "/api/setup"
        fun getPath(): String = PATH
    }
}