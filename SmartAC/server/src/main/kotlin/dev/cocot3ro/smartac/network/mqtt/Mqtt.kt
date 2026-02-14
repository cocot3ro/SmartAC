package dev.cocot3ro.smartac.network.mqtt

import dev.cocot3ro.smartac.network.NetworkRepository
import io.ktor.server.application.Application
import org.koin.ktor.ext.inject

fun Application.initMqtt() {
    val networkRepository: NetworkRepository by inject<NetworkRepository>()

    networkRepository.startMqttConnection()
}