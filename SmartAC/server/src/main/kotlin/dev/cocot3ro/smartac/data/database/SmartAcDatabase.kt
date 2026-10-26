package dev.cocot3ro.smartac.data.database

import androidx.room3.ColumnTypeConverters
import androidx.room3.Database
import androidx.room3.RoomDatabase
import dev.cocot3ro.smartac.data.database.converter.UuidConverter
import dev.cocot3ro.smartac.data.database.dao.AppConfigDao
import dev.cocot3ro.smartac.data.database.dao.DeviceUserDao
import dev.cocot3ro.smartac.data.database.model.AppConfigEntity
import dev.cocot3ro.smartac.data.database.model.DeviceUserEntity

internal const val DATABASE_NAME = "SmartAC.db"

@Database(
    version = 1,
    entities = [
        DeviceUserEntity::class,
        AppConfigEntity::class
    ]
)
@ColumnTypeConverters(
    UuidConverter::class
)
abstract class SmartAcDatabase : RoomDatabase() {
    abstract fun getAppConfigDao(): AppConfigDao
    abstract fun getDeviceUserDao(): DeviceUserDao
}
