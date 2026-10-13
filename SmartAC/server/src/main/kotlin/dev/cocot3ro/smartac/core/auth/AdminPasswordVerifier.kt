package dev.cocot3ro.smartac.core.auth

interface AdminPasswordVerifier {

    suspend fun verifyPassword(password: String): Boolean

}