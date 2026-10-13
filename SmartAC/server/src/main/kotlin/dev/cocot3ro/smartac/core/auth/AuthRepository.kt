package dev.cocot3ro.smartac.core.auth

import dev.cocot3ro.smartac.ADMIN_PASSWORD_REGEX
import dev.cocot3ro.smartac.data.database.repository.AppConfigRepository
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class AuthRepository(
    @Provided private val appConfigRepository: AppConfigRepository,
    @Provided private val passwordHasher: PasswordHasher
) : AdminPasswordVerifier, AdminAuthRepository {

    override suspend fun verifyPassword(password: String): Boolean {
        val adminPassword: String = appConfigRepository.getAdminConfig()?.configValue
            ?: return false

        return passwordHasher.verify(adminPassword, password)
    }

    override suspend fun setAdminPassword(password: String): Boolean {
        if (!ADMIN_PASSWORD_REGEX.toRegex().matches(password)) {
            return false
        }

        val hashedPassword: String = passwordHasher.hash(password)
        return appConfigRepository.setAdminPassword(hashedPassword)
    }

}
