package dev.cocot3ro.smartac.data.database.repository

import dev.cocot3ro.smartac.data.database.constants.AppConfigTable
import dev.cocot3ro.smartac.data.database.dao.AppConfigDao
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class AppConfigRepository(
    @Provided private val appConfigDao: AppConfigDao
) {

    suspend fun isPresentAdminPassword(): Boolean {
        return appConfigDao.get(AppConfigTable.Keys.ADMIN_PASSWORD) != null
    }

}
