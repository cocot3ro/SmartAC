package dev.cocot3ro.smartac.data.network.resources

import io.ktor.resources.Resource
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@OptIn(ExperimentalJsExport::class)
@Resource(VersionCheckRoute.PATH)
@JsExport
class VersionCheckRoute {
    companion object {
        private const val PATH = "/version/check"

        fun getPath(): String = PATH
    }
}