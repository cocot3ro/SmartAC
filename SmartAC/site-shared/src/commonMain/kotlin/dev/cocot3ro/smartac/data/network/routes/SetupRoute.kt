package dev.cocot3ro.smartac.data.network.routes

import io.ktor.resources.Resource

@Resource(SetupRoute.PATH)
class SetupRoute {

    companion object {
        private const val PATH = "/api/auth/setup"
        fun getPath(): String = PATH
    }
}