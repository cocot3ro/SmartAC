package dev.cocot3ro.smartac.core.state

enum class AcStatus {
    ONLINE,
    OFFLINE;

    companion object {
        fun valueOf(value: String, ignoreCase: Boolean): AcStatus {
            return AcStatus.entries.firstOrNull { it.name.equals(other = value, ignoreCase) }
                ?: throw IllegalArgumentException("No enum constant ${AcStatus::class.qualifiedName}.$value")
        }
    }
}
