package dev.cocot3ro.smartac.modules

import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.cors.routing.CORS

fun Application.configureCors() {

    if (developmentMode) {
        install(CORS) {
            anyHost()
            anyMethod()

            allowCredentials = true

            allowHeader(HttpHeaders.ContentType)
        }
    }
}
