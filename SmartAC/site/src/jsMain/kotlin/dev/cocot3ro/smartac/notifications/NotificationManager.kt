package dev.cocot3ro.smartac.notifications

import androidx.compose.runtime.mutableStateListOf
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

object NotificationManager {

    private val _notifications = mutableStateListOf<Notification>()

    val notifications: List<Notification>
        get() = _notifications.toList()

    private var nextId = 0L

    fun show(
        title: String,
        message: String,
        type: NotificationType = NotificationType.INFO,
        duration: Duration = 5.seconds
    ): Long {
        val id = nextId++

        _notifications.add(
            Notification(
                id = id,
                title = title,
                message = message,
                type = type,
                duration = duration
            )
        )

        return id
    }

    fun success(
        title: String,
        message: String,
        duration: Duration = 5.seconds
    ) = show(title, message, NotificationType.SUCCESS, duration)

    fun error(
        title: String,
        message: String,
        duration: Duration = 5.seconds
    ) = show(title, message, NotificationType.ERROR, duration)

    fun warning(
        title: String,
        message: String,
        duration: Duration = 5.seconds
    ) = show(title, message, NotificationType.WARNING, duration)

    fun info(
        title: String,
        message: String,
        duration: Duration = 5.seconds
    ) = show(title, message, NotificationType.INFO, duration)

    fun dismiss(id: Long) {
        _notifications.removeAll { it.id == id }
    }

    fun dismissAll() {
        _notifications.clear()
    }
}