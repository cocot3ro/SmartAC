package dev.cocot3ro.smartac.data.database.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import dev.cocot3ro.smartac.data.database.constants.AppConfigTable.Columns
import dev.cocot3ro.smartac.data.database.constants.AppConfigTable.TABLE_NAME
import dev.cocot3ro.smartac.data.database.model.AppConfigEntity

@Dao
interface AppConfigDao {

    @Insert
    suspend fun insert(item: AppConfigEntity): Long

    @Query("SELECT * FROM $TABLE_NAME WHERE ${Columns.CONFIG_KEY} = :key")
    suspend fun get(key: String): AppConfigEntity?
}
