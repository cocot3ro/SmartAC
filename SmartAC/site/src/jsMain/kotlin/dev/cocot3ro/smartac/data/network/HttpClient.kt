package dev.cocot3ro.smartac.data.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.cookies.HttpCookies
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.resources.Resources
import io.ktor.serialization.kotlinx.json.json
import org.koin.core.annotation.Factory

@Factory
fun provideHttpClient(): HttpClient = HttpClient {
    install(ContentNegotiation) {
        json()
    }

    install(Resources)

    install(HttpCookies)
}