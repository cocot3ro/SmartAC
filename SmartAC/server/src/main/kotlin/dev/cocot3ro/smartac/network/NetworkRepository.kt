package dev.cocot3ro.smartac.network

import com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client
import dev.cocot3ro.smartac.network.http.model.AcStatusModel
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single

@Single
class NetworkRepository(
    @Provided private val client: Mqtt5AsyncClient
) {

    fun getAcStatus(): AcStatusModel {

    }

}