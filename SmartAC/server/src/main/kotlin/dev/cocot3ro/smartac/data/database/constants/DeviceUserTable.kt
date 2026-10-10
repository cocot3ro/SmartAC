package dev.cocot3ro.smartac.data.database.constants

object DeviceUserTable {

    const val TABLE_NAME = "device_user"

    object Columns {
        const val ID: String = "id"
        const val PASSWORD_HASH: String = "password_hash"
    }
}
