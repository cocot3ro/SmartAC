package dev.cocot3ro.smartac.data.network

import dev.cocot3ro.smartac.domain.model.AcControl
import dev.cocot3ro.smartac.domain.model.toModel
import dev.cocot3ro.smartac.network.http.model.WebSocketMessage
import dev.cocot3ro.smartac.network.http.resources.Api
import io.ktor.client.HttpClient
import io.ktor.client.plugins.resources.post
import io.ktor.client.plugins.websocket.receiveDeserialized
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.path
import io.ktor.websocket.CloseReason
import io.ktor.websocket.close
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.isActive
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single

@Single
class NetworkRepository(
    @Provided private val httpClient: HttpClient
) {

    fun connectWebsocket(): Flow<WebSocketMessage> = callbackFlow {
        val session = httpClient.webSocketSession {
            url {
                protocol = URLProtocol.WSS
                host = "smartac.cocot3ro.freeddns.org" //TODO: Move to config
                port = protocol.defaultPort
                path(Api.State.getRoute())
            }
        }

        try {
            while (isActive) {
                val msg = session.receiveDeserialized<WebSocketMessage>()
                send(msg)
            }
        } finally {
            session.close(CloseReason(CloseReason.Codes.NORMAL, "Client closed"))
        }

        awaitClose()
    }

    suspend fun sendPower(power: AcControl.Power) {
        httpClient.post(resource = Api.Control.Power()) {
            contentType(type = ContentType.Application.Json)
            setBody(body = power.toModel())
        }
    }

    suspend fun sendTemperature(temperature: AcControl.Temperature) {
        httpClient.post(resource = Api.Control.Temperature()) {
            contentType(type = ContentType.Application.Json)
            setBody(body = temperature.toModel())
        }
    }

    suspend fun sendMode(mode: AcControl.Mode) {
        httpClient.post(resource = Api.Control.Mode()) {
            contentType(type = ContentType.Application.Json)
            setBody(body = mode.toModel())
        }
    }

    suspend fun sendFanSpeed(fanSpeed: AcControl.FanSpeed) {
        httpClient.post(resource = Api.Control.FanSpeed()) {
            contentType(type = ContentType.Application.Json)
            setBody(body = fanSpeed.toModel())
        }
    }
}
