package dev.cocot3ro.smartac.network.mqtt.client

import com.hivemq.client.mqtt.mqtt5.Mqtt5Client
import org.koin.core.annotation.Single

@Single
fun provideMqtt5Client(): Mqtt5Client {
    return Mqtt5Client.builder()
        .identifier("smart-ac-server")
        .serverHost("raspberry.local")
        .serverPort(1883)
        .buildAsync().also { it.connect() }
}