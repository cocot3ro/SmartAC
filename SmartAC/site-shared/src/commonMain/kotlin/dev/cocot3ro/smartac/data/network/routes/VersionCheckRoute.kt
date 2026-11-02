package dev.cocot3ro.smartac.data.network.routes

import io.ktor.resources.Resource

@Resource(VersionCheckRoute.PATH)
class VersionCheckRoute {
    companion object {
        private const val PATH = "/api/version/check"

        fun getPath(): String = PATH
    }
}