package dev.cocot3ro.smartac

import dev.cocot3ro.smartac.network.mqtt.initMqtt
import io.ktor.server.application.Application

fun Application.module() {
    configureSockets()
    configureFrameworks()
    configureSerialization()
    configureMonitoring()
    configureSecurity()
    configureRouting()

    initMqtt()
}
