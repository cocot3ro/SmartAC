package dev.cocot3ro.smartac.data.database.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import dev.cocot3ro.smartac.data.database.constants.DeviceUserTable.Columns
import dev.cocot3ro.smartac.data.database.constants.DeviceUserTable.TABLE_NAME
import dev.cocot3ro.smartac.data.database.model.DeviceUser
import kotlin.uuid.Uuid

@Dao
interface DeviceUserDao {

    @Query("SELECT * FROM $TABLE_NAME WHERE ${Columns.ID} = :id")
    suspend fun getById(id: Uuid): DeviceUser?

    @Insert
    suspend fun insert(deviceUser: DeviceUser): Long

}
