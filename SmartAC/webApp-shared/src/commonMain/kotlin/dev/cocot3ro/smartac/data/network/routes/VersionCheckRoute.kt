package dev.cocot3ro.smartac.data.network.routes

import io.ktor.resources.Resource
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@OptIn(ExperimentalJsExport::class)
@Resource(VersionCheckRoute.PATH)
@JsExport
class VersionCheckRoute {
    companion object {
        private const val PATH = "/api/version/check"

        fun getPath(): String = PATH
    }
}