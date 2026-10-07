package dev.cocot3ro.smartac.data.database

import androidx.room3.Room
import androidx.room3.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import dev.cocot3ro.smartac.data.database.dao.AppConfigDao
import kotlinx.coroutines.Dispatchers
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single
import java.io.File

@Single
fun provideDatabaseBuilder(): RoomDatabase.Builder<SmartAcDatabase> {
    val dbFile = File("/data", DATABASE_NAME)
//    return Room.databaseBuilder<SmartAcDatabase>(
//        name = dbFile.absolutePath,
//    )
    return Room.inMemoryDatabaseBuilder<SmartAcDatabase>()
}

@Single
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
