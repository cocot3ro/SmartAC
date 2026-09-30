package dev.cocot3ro.smartac.data.network.resources

import io.ktor.resources.Resource
import kotlinx.serialization.Serializable

@Resource(Foo.PATH)
@Serializable
class Foo {

    companion object {
        private const val PATH = "/api"
    }
}