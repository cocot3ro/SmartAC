package dev.cocot3ro.smartac.domain.usecase

import dev.cocot3ro.smartac.data.database.repository.AppConfigRepository
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class IsSetupUseCase(
    @Provided private val appConfigRepository: AppConfigRepository
) {

    suspend operator fun invoke(): Boolean = appConfigRepository.hasAdminPassword()
}
