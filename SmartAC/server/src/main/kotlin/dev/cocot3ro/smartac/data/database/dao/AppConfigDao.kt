package dev.cocot3ro.smartac.data.database.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import dev.cocot3ro.smartac.data.database.model.AppConfigEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AppConfigDao {

    @Insert
    suspend fun insert(item: AppConfigDao)

    @Query("SELECT * FROM AppConfigEntity")
    fun getAllAsFlow(): Flow<AppConfigEntity>

    @Query("SELECT * FROM AppConfigEntity WHERE id = ?")
    suspend fun get(key: String): AppConfigEntity?
}
