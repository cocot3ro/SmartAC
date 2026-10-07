package dev.cocot3ro.smartac.utils

import dev.cocot3ro.smartac.data.database.repository.AppConfigRepository
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

fun interface IsSetup {
    suspend operator fun invoke(): Boolean
}

@Factory
fun isSetup(@Provided appConfigRepository: AppConfigRepository): IsSetup = IsSetup {
    appConfigRepository.isPresentAdminPassword()
}