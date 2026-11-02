package dev.cocot3ro.smartac.data.network.routes

import io.ktor.resources.Resource

@Resource(UsersRoute.PATH)
class UsersRoute {
    companion object {
        private const val PATH = "/api/user"

        fun getPath(): String = PATH
    }
}