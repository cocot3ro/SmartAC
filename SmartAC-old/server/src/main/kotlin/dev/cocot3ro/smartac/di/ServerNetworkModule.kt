package dev.cocot3ro.smartac.di

import com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client
import dev.cocot3ro.smartac.network.NetworkRepository
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.plugin.module.dsl.single

val networkModule: Module = module {
    single<NetworkRepository>()

    single<Mqtt5AsyncClient> { _ ->
        Mqtt5Client.builder()
            .identifier(getProperty<String>("MQTT_CLIENT_ID"))
            .serverHost(getProperty<String>("MQTT_BROKER_HOST"))
            .serverPort(getProperty<String>("MQTT_BROKER_PORT").toInt())
            .buildAsync()
    }
}