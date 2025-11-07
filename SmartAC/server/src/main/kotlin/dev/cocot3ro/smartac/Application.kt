package dev.cocot3ro.smartac

import io.ktor.server.application.Application

fun Application.module() {
    configureSockets()
    configureFrameworks()
    configureSerialization()
    configureMonitoring()
    configureSecurity()
    configureRouting()
}
