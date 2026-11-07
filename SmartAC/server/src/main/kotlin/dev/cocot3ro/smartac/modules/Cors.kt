package dev.cocot3ro.smartac.modules

import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.cors.routing.CORS

fun Application.configureCors() {

    if (developmentMode) {
        install(CORS) {
            allowHost("localhost:8082", schemes = listOf("http"))

            allowCredentials = true

            allowMethod(HttpMethod.Get)
            allowMethod(HttpMethod.Post)
            allowMethod(HttpMethod.Put)
            allowMethod(HttpMethod.Delete)

            allowHeader(HttpHeaders.ContentType)
        }
    }
}