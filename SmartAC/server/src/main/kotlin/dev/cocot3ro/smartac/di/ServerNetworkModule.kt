package dev.cocot3ro.smartac.di

import com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client
import dev.cocot3ro.smartac.network.NetworkRepository
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val networkModule: Module = module {
    singleOf(::NetworkRepository)

    single<Mqtt5AsyncClient> {
        Mqtt5Client.builder()
            .identifier(getProperty<String>("mqtt_client_id"))
            .serverHost(getProperty<String>("mqtt_broker_host"))
            .serverPort(getProperty<String>("mqtt_broker_port").toInt())
            .buildAsync()
    }
}