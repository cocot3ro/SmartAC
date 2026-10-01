package dev.cocot3ro.smartac.data.network.resources

import io.ktor.resources.Resource
import kotlinx.serialization.Serializable

@Resource(LoginRoute.PATH)
@Serializable
class LoginRoute {

    companion object {
        private const val PATH = "/login"
    }
}