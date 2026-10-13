package dev.cocot3ro.smartac.core.auth

interface AdminAuthRepository {

    suspend fun setAdminPassword(password: String): Boolean

}