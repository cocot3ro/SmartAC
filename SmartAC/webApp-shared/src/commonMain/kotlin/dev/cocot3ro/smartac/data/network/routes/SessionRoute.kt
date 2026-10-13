package dev.cocot3ro.smartac.data.network.routes

import io.ktor.resources.Resource
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@OptIn(ExperimentalJsExport::class)
@Resource(SessionRoute.PATH)
@JsExport
class SessionRoute {

    companion object {
        private const val PATH = "/api/session"
        fun getPath(): String = PATH
    }
}