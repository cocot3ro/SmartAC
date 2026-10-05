package dev.cocot3ro.smartac.data.database.model

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import androidx.room3.ColumnInfo

@Entity
data class AppConfigEntity(
    @PrimaryKey
    @ColumnInfo(name = "config_key")
    val configKey: String,

    @ColumnInfo(name = "config_value")
    val configValue: String
)