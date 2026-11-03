package dev.cocot3ro.smartac.data.network.routes

import io.ktor.resources.Resource

@Resource(SessionRoute.PATH)
class SessionRoute {

    companion object {
        private const val PATH = "/api/auth/session"
        fun getPath(): String = PATH
    }
}