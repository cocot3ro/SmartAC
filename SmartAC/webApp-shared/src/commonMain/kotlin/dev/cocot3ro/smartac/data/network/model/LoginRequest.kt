package dev.cocot3ro.smartac.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val password: String
)