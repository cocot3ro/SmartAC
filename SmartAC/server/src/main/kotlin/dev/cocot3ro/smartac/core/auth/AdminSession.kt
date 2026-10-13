package dev.cocot3ro.smartac.core.auth

import kotlinx.serialization.Serializable

@Serializable
data class AdminSession(val authenticated: Boolean = true)
