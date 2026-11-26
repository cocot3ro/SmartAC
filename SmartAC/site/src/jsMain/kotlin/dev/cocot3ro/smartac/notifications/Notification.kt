package dev.cocot3ro.smartac.notifications

import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

enum class NotificationType {
    SUCCESS,
    ERROR,
    WARNING,
    INFO
}

data class Notification(
    val id: Long,
    val title: String,
    val message: String,
    val type: NotificationType,
    val duration: Duration = 5.seconds
)
