package dev.cocot3ro.smartac.data.database.repository

import dev.cocot3ro.smartac.data.database.dao.DeviceUserDao
import dev.cocot3ro.smartac.data.database.model.DeviceUser
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided
import kotlin.uuid.Uuid

@Factory
class DeviceUserRepository(
    @Provided private val deviceUserDao: DeviceUserDao
) {

    suspend fun getById(id: Uuid): DeviceUser? {
        return deviceUserDao.getById(id)
    }

    suspend fun insertUser(deviceUser: DeviceUser) {
        deviceUserDao.insert(deviceUser)
    }
}
