package dev.cocot3ro.smartac.data.network.routes

import io.ktor.resources.Resource

@Resource(LoginRoute.PATH)
class LoginRoute {
    companion object {
        private const val PATH = "/api/login"
        fun getPath() = PATH
    }
}