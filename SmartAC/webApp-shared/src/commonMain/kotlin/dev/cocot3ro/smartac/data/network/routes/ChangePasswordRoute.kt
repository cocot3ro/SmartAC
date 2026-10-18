package dev.cocot3ro.smartac.data.network.routes

import io.ktor.resources.Resource
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@OptIn(ExperimentalJsExport::class)
@Resource(ChangePasswordRoute.PATH)
@JsExport
class ChangePasswordRoute {

    companion object {
        private const val PATH = "/api/change-password"
        fun getPath() = PATH
    }
}