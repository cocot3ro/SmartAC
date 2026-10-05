package dev.cocot3ro.smartac.data.database

import androidx.room3.Database
import androidx.room3.RoomDatabase
import dev.cocot3ro.smartac.data.database.dao.AppConfigDao
import dev.cocot3ro.smartac.data.database.model.AppConfigEntity

internal const val DATABASE_NAME = "SmartAC.db"

@Database(
    version = 1,
    entities = [
        AppConfigEntity::class
    ]
)
abstract class SmartAcDatabase : RoomDatabase() {
    abstract fun getAppConfigDao(): AppConfigDao
}
