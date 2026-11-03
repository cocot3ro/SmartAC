package dev.cocot3ro.smartac.data.network.routes

import io.ktor.resources.Resource

@Resource(ChangePasswordRoute.PATH)
class ChangePasswordRoute {

    companion object {
        private const val PATH = "/api/auth/change-password"
        fun getPath() = PATH
    }
}