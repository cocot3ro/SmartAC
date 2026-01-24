package dev.cocot3ro.smartac.network.http.routing

import dev.cocot3ro.smartac.core.state.AcFlowState
import dev.cocot3ro.smartac.core.state.StateRepository
import dev.cocot3ro.smartac.core.state.toModel
import dev.cocot3ro.smartac.network.NetworkRepository
import dev.cocot3ro.smartac.network.http.model.AcControlModel
import dev.cocot3ro.smartac.network.http.model.AcStatusModel
import dev.cocot3ro.smartac.network.http.model.WebSocketMessage
import dev.cocot3ro.smartac.network.http.resources.Api
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.request.receive
import io.ktor.server.resources.post
import io.ktor.server.routing.routing
import io.ktor.server.websocket.sendSerialized
import io.ktor.server.websocket.webSocket
import io.ktor.util.reflect.typeInfo
import org.koin.ktor.ext.inject

@OptIn(ExperimentalUnsignedTypes::class)
fun Application.apiRouting() {

    val stateRepository: StateRepository by inject()
    val networkRepository: NetworkRepository by inject()

    routing {
//        authenticate("api-key-auth") {
        webSocket(path = Api.State.getRoute()) {
            stateRepository.state.collect { flowState ->
                when (flowState) {
                    AcFlowState.Idle -> return@collect

                    is AcFlowState.Online -> {
                        sendSerialized(
                            data = AcStatusModel.ONLINE,
                            typeInfo = typeInfo<WebSocketMessage>()
                        )

                        sendSerialized(
                            data = flowState.acState.toModel(),
                            typeInfo = typeInfo<WebSocketMessage>()
                        )
                    }

                    AcFlowState.Offline -> {
                        sendSerialized(
                            data = AcStatusModel.OFFLINE,
                            typeInfo = typeInfo<WebSocketMessage>()
                        )
                    }
                }
            }
        }

        post<Api.Control.Power> { _ ->
            val powerModel: AcControlModel.Power = call.receive<AcControlModel.Power>()

            val result: UByteArray? = stateRepository.withPower(powerModel)

            if (result != null) {
                networkRepository.sendIrCommand(data = result)
            }
        }

        post<Api.Control.Mode> { _ ->
            val modeModel: AcControlModel.Mode = call.receive<AcControlModel.Mode>()

            val result: UByteArray? = stateRepository.withMode(modeModel)

            if (result != null) {
                networkRepository.sendIrCommand(data = result)
            }
        }

        post<Api.Control.Temperature> { _ ->
            val tempModel: AcControlModel.Temperature = call.receive<AcControlModel.Temperature>()

            val result: UByteArray? = stateRepository.withTemp(tempModel)

            if (result != null) {
                networkRepository.sendIrCommand(data = result)
            }

            call.response.status(value = HttpStatusCode.OK)
        }

        post<Api.Control.FanSpeed> { _ ->
            val fanSpeedModel: AcControlModel.FanSpeed = call.receive<AcControlModel.FanSpeed>()

            val result: UByteArray? = stateRepository.withFanSpeed(fanSpeedModel)

            if (result != null) {
                networkRepository.sendIrCommand(data = result)
            }
        }
//        }
    }
}
