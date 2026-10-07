package dev.cocot3ro.smartac.data.network.resources

import io.ktor.resources.Resource
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@OptIn(ExperimentalJsExport::class)
@Resource(TokensRoute.PATH)
@JsExport
class TokensRoute {

    companion object {
        private const val PATH: String = "/tokens"

        fun getPath(): String = PATH
    }
}