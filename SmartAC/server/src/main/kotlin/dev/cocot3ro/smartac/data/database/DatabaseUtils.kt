package dev.cocot3ro.smartac.data.database

import androidx.room3.Room
import androidx.room3.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import dev.cocot3ro.smartac.data.database.dao.AppConfigDao
import dev.cocot3ro.smartac.data.database.dao.DeviceUserDao
import kotlinx.coroutines.Dispatchers
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Property
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single
import java.io.File

@Factory
fun provideDatabaseBuilder(
    @Provided @Property("data_dir") dataDir: String
): RoomDatabase.Builder<SmartAcDatabase> {
    val dbFile = File(dataDir, DATABASE_NAME)
    return Room.databaseBuilder<SmartAcDatabase>(
        name = dbFile.absolutePath,
    )
}

@Single(createdAtStart = true)
fun provideRoomDatabase(
    @Provided builder: RoomDatabase.Builder<SmartAcDatabase>
): SmartAcDatabase = builder
    .setDriver(BundledSQLiteDriver())
    .setQueryCoroutineContext(Dispatchers.IO)
    .build()

@Factory
fun provideAppConfigDao(
    @Provided db: SmartAcDatabase
): AppConfigDao = db.getAppConfigDao()

@Factory
fun provideDeviceUserDao(
    @Provided db: SmartAcDatabase
): DeviceUserDao = db.getDeviceUserDao()
