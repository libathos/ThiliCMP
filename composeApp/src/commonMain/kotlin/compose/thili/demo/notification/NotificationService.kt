@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package compose.thili.demo.notification

/**
 * Interface for scheduling notifications across different platforms.
 */
interface NotificationService {
    /**
     * Schedules a notification to be shown at the specified date and time.
     *
     * @param title The title of the notification
     * @param message The message/content of the notification
     * @param dateString The date in format "dd/MM/yyyy"
     * @param timeString The time in format "HH:mm"
     * @param notes Additional notes or information to include (optional)
     * @return Boolean indicating whether the notification was successfully scheduled
     */
    fun scheduleNotification(
        title: String,
        message: String,
        dateString: String,
        timeString: String,
        notes: String = ""
    ): Boolean
    
    /**
     * Parses date and time strings into a timestamp in milliseconds.
     *
     * @param dateString The date in format "dd/MM/yyyy"
     * @param timeString The time in format "HH:mm"
     * @return Long representing the timestamp in milliseconds
     */
    fun parseDateTime(dateString: String, timeString: String): Long
}

/**
 * Factory for creating platform-specific notification service implementations.
 */
expect object NotificationServiceFactory {
    /**
     * Creates a platform-specific implementation of NotificationService.
     *
     * @return NotificationService implementation for the current platform
     */
    fun createNotificationService(): NotificationService
}