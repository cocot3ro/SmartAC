package dev.cocot3ro.smartac.domain.model

enum class Status {
    ONLINE,
    OFFLINE,
    CONNECTING,
    CONNECTION_ERROR,
    DISCONNECTED;

    companion object {
        fun valueOf(value: String, ignoreCase: Boolean): Status {
            return entries.firstOrNull { it.name.equals(other = value, ignoreCase) }
                ?: throw IllegalArgumentException("No enum constant ${Status::class.qualifiedName}.$value")
        }
    }
}