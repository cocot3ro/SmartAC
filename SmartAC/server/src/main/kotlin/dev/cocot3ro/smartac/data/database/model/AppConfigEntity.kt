package dev.cocot3ro.smartac.data.database.model

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.PrimaryKey
import dev.cocot3ro.smartac.data.database.constants.AppConfigTable
import dev.cocot3ro.smartac.data.database.constants.AppConfigTable.Columns

@Entity(AppConfigTable.TABLE_NAME)
data class AppConfigEntity(
    @PrimaryKey
    @ColumnInfo(name = Columns.CONFIG_KEY)
    val configKey: String,

    @ColumnInfo(name = Columns.CONFIG_VALUE)
    val configValue: String
)
