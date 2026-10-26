package dev.cocot3ro.smartac.data.database.repository

import dev.cocot3ro.smartac.data.database.dao.DeviceUserDao
import dev.cocot3ro.smartac.data.database.model.DeviceUserEntity
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided
import kotlin.uuid.Uuid

@Factory
class DeviceUserRepository(
    @Provided private val deviceUserDao: DeviceUserDao
) {

    suspend fun getById(id: Uuid): DeviceUserEntity? {
        return deviceUserDao.getById(id)
    }

    suspend fun insertUser(deviceUser: DeviceUserEntity) {
        deviceUserDao.insert(deviceUser)
    }
}
