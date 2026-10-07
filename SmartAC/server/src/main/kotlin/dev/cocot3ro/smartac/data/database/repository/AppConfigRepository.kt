package dev.cocot3ro.smartac.data.database.repository

import dev.cocot3ro.smartac.data.database.DatabaseConstants
import dev.cocot3ro.smartac.data.database.dao.AppConfigDao
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class AppConfigRepository(
    @Provided private val appConfigDao: AppConfigDao
) {

    suspend fun isPresentAdminPassword(): Boolean {
        return appConfigDao.get(DatabaseConstants.AppConfig.Keys.ADMIN_PASSWORD) != null
    }

}
