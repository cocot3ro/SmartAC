package dev.cocot3ro.smartac.data.network.routes

import io.ktor.resources.Resource

@Resource(LogoutRoute.PATH)
class LogoutRoute {

    companion object {
        private const val PATH = "/api/logout"

        fun getPath(): String = PATH
    }
}