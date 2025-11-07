package dev.cocot3ro.smartac.network.http.resources

import io.ktor.resources.Resource

@Resource(Api.PATH)
class Api {

    companion object : ResourceRoute {
        const val PATH: String = "/api"

        override fun getRoute(): String = PATH
    }

    @Resource(Ac.PATH)
    data class Ac(val parent: Api = Api()) {

        companion object : ResourceRoute {

            const val PATH: String = "ac"

            override fun getRoute(): String = "${Api.getRoute()}/$PATH"
        }


        @Resource("status")
        data class Status(val parent: Ac = Ac()) {

            companion object : ResourceRoute {
                const val PATH: String = "status"

                override fun getRoute(): String = "${Ac.getRoute()}/$PATH"
            }
        }
    }
}