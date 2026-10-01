package dev.cocot3ro.smartac.data.network.resources

import io.ktor.resources.Resource
import kotlinx.serialization.Serializable

@Resource(LogoutRoute.PATH)
@Serializable
class LogoutRoute {

    companion object {
        private const val PATH = "/logout"
    }
}