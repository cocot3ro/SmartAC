package dev.cocot3ro.smartac.data.database.model

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.PrimaryKey
import dev.cocot3ro.smartac.data.database.constants.DeviceUserTable.Columns
import dev.cocot3ro.smartac.data.database.constants.DeviceUserTable
import kotlin.uuid.Uuid

@Entity(DeviceUserTable.TABLE_NAME)
data class DeviceUserEntity(
    @PrimaryKey
    @ColumnInfo(name = Columns.ID)
    val id: Uuid,

    @ColumnInfo(name = Columns.PASSWORD_HASH)
    val passwordHash: ByteArray
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DeviceUserEntity

        if (id != other.id) return false
        if (!passwordHash.contentEquals(other.passwordHash)) return false

        return true
    }

    override fun hashCode(): Int {
        var result: Int = id.hashCode()
        result = 31 * result + passwordHash.contentHashCode()
        return result
    }
}
