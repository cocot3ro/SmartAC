package dev.cocot3ro.smartac.data.network.routes

import io.ktor.resources.Resource
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@OptIn(ExperimentalJsExport::class)
@Resource(LoginRoute.PATH)
@JsExport
class LoginRoute {
    companion object {
        private const val PATH = "/api/login"
        fun getPath() = PATH
    }
}