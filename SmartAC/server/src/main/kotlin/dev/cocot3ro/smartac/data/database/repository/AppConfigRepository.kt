package dev.cocot3ro.smartac.data.database.repository

import dev.cocot3ro.smartac.data.database.constants.AppConfigTable.Keys
import dev.cocot3ro.smartac.data.database.dao.AppConfigDao
import dev.cocot3ro.smartac.data.database.model.AppConfigEntity
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class AppConfigRepository(
    @Provided private val appConfigDao: AppConfigDao
) {

    suspend fun getAdminConfig(): AppConfigEntity? {
        return appConfigDao.get(Keys.ADMIN_PASSWORD)
    }

    suspend fun isPresentAdminPassword(): Boolean {
        return getAdminConfig() != null
    }

    suspend fun setAdminPassword(password: String): Boolean {
        getAdminConfig()?.let {
            return appConfigDao.update(
                item = AppConfigEntity(
                    configKey = Keys.ADMIN_PASSWORD,
                    configValue = password
                )
            ) > 0
        }

        return appConfigDao.insert(
            item = AppConfigEntity(
                configKey = Keys.ADMIN_PASSWORD,
                configValue = password
            )
        ) != -1L
    }
}
