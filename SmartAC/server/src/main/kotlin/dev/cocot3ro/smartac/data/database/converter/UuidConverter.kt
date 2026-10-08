package dev.cocot3ro.smartac.data.database.converter

import androidx.room3.ColumnTypeConverter
import kotlin.uuid.Uuid

object UuidConverter {

    @ColumnTypeConverter
    fun fromUuid(uuid: Uuid): String {
        return uuid.toString()
    }

    @ColumnTypeConverter
    fun toUuid(uuidString: String): Uuid {
        return Uuid.parse(uuidString)
    }
}