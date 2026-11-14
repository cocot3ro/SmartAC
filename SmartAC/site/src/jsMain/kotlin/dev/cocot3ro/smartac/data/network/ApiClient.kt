package dev.cocot3ro.smartac.data.network

import dev.cocot3ro.smartac.data.network.model.LoginRequest
import dev.cocot3ro.smartac.data.network.routes.HealthRoute
import dev.cocot3ro.smartac.data.network.routes.LoginRoute
import dev.cocot3ro.smartac.data.network.routes.LogoutRoute
import dev.cocot3ro.smartac.data.network.routes.SessionRoute
import dev.cocot3ro.smartac.data.network.routes.SetupRoute
import io.ktor.client.HttpClient
import io.ktor.client.fetchOptions
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.post
import io.ktor.client.statement.HttpResponse
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class ApiClient(
    @Provided private val httpClient: HttpClient
) {

    suspend fun getHealth(): HttpResponse {
        return httpClient.get(resource = HealthRoute())
    }

    suspend fun setup(req: LoginRequest): HttpResponse {
        return httpClient.post(resource = SetupRoute()) {
            setJsonBody(req)
        }
    }

    suspend fun checkSession(): HttpResponse {
        return httpClient.get(resource = SessionRoute()) {
            fetchOptions {
                credentials = "include"
            }
        }
    }

    suspend fun login(req: LoginRequest): HttpResponse {
        return httpClient.post(resource = LoginRoute()) {
            setJsonBody(req)

            fetchOptions {
                credentials = "include"
            }
        }
    }

    suspend fun logout(): HttpResponse {
        return httpClient.post(resource = LogoutRoute()) {
            fetchOptions {
                credentials = "include"
            }
        }
    }
}
