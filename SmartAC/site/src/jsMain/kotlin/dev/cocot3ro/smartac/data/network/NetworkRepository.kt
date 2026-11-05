package dev.cocot3ro.smartac.data.network

import dev.cocot3ro.smartac.data.network.model.HealthResponse
import dev.cocot3ro.smartac.data.network.model.LoginRequest
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class NetworkRepository(
    @Provided private val apiClient: ApiClient
) {

    suspend fun getHealth(): Result<HealthResponse> = runCatching {
        val response: HttpResponse = apiClient.getHealth()
        return@runCatching response.body<HealthResponse>()
    }

    suspend fun checkSession(): Result<Boolean> = runCatching {
        val response: HttpResponse = apiClient.checkSession()
        return@runCatching response.status.isSuccess()
    }

    suspend fun setup(password: String): Result<Boolean> = runCatching {
        val response: HttpResponse = apiClient.setup(req = LoginRequest(password))
        return@runCatching response.status.isSuccess()
    }

    suspend fun login(password: String): Result<Boolean> = runCatching {
        val response: HttpResponse = apiClient.login(req = LoginRequest(password))
        return@runCatching response.status.isSuccess()
    }

    suspend fun logout(): Result<Unit> = runCatching {
        apiClient.logout()
    }
}
