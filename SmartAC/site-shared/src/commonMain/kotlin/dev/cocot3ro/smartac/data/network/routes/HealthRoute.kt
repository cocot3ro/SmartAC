package dev.cocot3ro.smartac.data.network.routes

import io.ktor.resources.Resource

@Resource(HealthRoute.PATH)
class HealthRoute {
    companion object {
        private const val PATH = "/api/health"

        fun getPath() = PATH
    }
}
