package dev.cocot3ro.smartac.data.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.resources.Resources
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Property
import org.koin.core.annotation.Single

@Single
fun provideHttpClient(@Property("api_key") apiKey: String): HttpClient {
    return HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json()
        }

        install(Resources)

        install(WebSockets) {
            contentConverter = KotlinxWebsocketSerializationConverter(Json {
                ignoreUnknownKeys = true
                classDiscriminator = "type"
                useArrayPolymorphism = true
            })
        }

        install(Auth) {
            bearer {
                loadTokens {
                    BearerTokens(accessToken = apiKey, refreshToken = null)
                }
            }
        }

        defaultRequest {
            url {
                host = "smartac.cocot3ro.freeddns.org"
                port = 443
                protocol = URLProtocol.HTTPS
            }
        }
    }
}