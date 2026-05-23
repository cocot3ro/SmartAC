package dev.cocot3ro.smartac.network.http.resources

import io.ktor.resources.Resource

@Resource(Api.PATH)
class Api {

    companion object : ResourceRoute {
        const val PATH: String = "/api"
        override fun getRoute(): String = PATH
    }

    @Resource(State.PATH)
    data class State(val parent: Api = Api()) {
        companion object : ResourceRoute {
            const val PATH: String = "state"
            override fun getRoute(): String = "${Api.getRoute()}/$PATH"
        }
    }

    @Resource(Control.PATH)
    data class Control(val parent: Api = Api()) {
        companion object : ResourceRoute {
            const val PATH: String = "control"
            override fun getRoute(): String = "${Api.getRoute()}/$PATH"
        }

        @Resource(Power.PATH)
        data class Power(val parent: Control = Control()) {
            companion object : ResourceRoute {
                const val PATH: String = "power"
                override fun getRoute(): String = "${Control.getRoute()}/$PATH"
            }
        }

        @Resource(Mode.PATH)
        data class Mode(val parent: Control = Control()) {
            companion object : ResourceRoute {
                const val PATH: String = "mode"
                override fun getRoute(): String = "${Control.getRoute()}/$PATH"
            }
        }

        @Resource(Temperature.PATH)
        data class Temperature(val parent: Control = Control()) {
            companion object : ResourceRoute {
                const val PATH: String = "temperature"
                override fun getRoute(): String = "${Control.getRoute()}/$PATH"
            }
        }

        @Resource(FanSpeed.PATH)
        data class FanSpeed(val parent: Control = Control()) {
            companion object : ResourceRoute {
                const val PATH: String = "fanSpeed"
                override fun getRoute(): String = "${Control.getRoute()}/$PATH"
            }
        }
    }
}
