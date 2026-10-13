package dev.cocot3ro.smartac.data.network.routes

import io.ktor.resources.Resource
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@OptIn(ExperimentalJsExport::class)
@Resource(LogoutRoute.PATH)
@JsExport
class LogoutRoute {

    companion object {
        private const val PATH = "/api/logout"

        fun getPath(): String = PATH
    }
}