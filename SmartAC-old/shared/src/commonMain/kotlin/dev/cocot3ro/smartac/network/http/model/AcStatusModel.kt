package dev.cocot3ro.smartac.network.http.model

import kotlinx.serialization.Serializable

@Serializable
enum class AcStatusModel : WebSocketMessage {
    ONLINE,
    OFFLINE;

    override val type: String = "status"
}
