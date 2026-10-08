package dev.cocot3ro.smartac.data.database.model

import androidx.room3.ColumnInfo
import androidx.room3.ColumnTypeConverter
import androidx.room3.Entity
import androidx.room3.PrimaryKey
import kotlin.uuid.Uuid

@Entity
data class DeviceUser(
    @PrimaryKey
    @ColumnInfo(name = "device_user_id_pk")
    val id: Uuid,

    @ColumnInfo(name = "password_hash")
    val passwordHash: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DeviceUser

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
