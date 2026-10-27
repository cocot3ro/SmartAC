package dev.cocot3ro.smartac.data.network.routes

import io.ktor.resources.Resource
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@OptIn(ExperimentalJsExport::class)
@Resource(UsersRoute.PATH)
@JsExport
class UsersRoute {
    companion object {
        private const val PATH = "/api/user"

        fun getPath(): String = PATH
    }
}