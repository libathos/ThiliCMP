package compose.thili.demo.notification

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

/**
 * Common utility functions for notification handling across platforms.
 */
object CommonNotificationUtils {
    /**
     * Parses date and time strings and calculates the time in milliseconds from now.
     *
     * @param dateString The date in format "dd/MM/yyyy"
     * @param timeString The time in format "HH:mm"
     * @return Long representing the absolute time in milliseconds when the notification should be triggered
     */
    fun parseDateTime(dateString: String, timeString: String): Long {
        // Parse date (dd/MM/yyyy)
        val dateParts = dateString.split("/")
        if (dateParts.size != 3) {
            throw IllegalArgumentException("Invalid date format. Expected dd/MM/yyyy, got $dateString")
        }

        val day = dateParts[0].toInt()
        val month = dateParts[1].toInt()
        val year = dateParts[2].toInt()

        // Parse time (HH:mm)
        val timeParts = timeString.split(":")
        if (timeParts.size != 2) {
            throw IllegalArgumentException("Invalid time format. Expected HH:mm, got $timeString")
        }

        val hour = timeParts[0].toInt()
        val minute = timeParts[1].toInt()

        // Get current time
        val now = Clock.System.now()
        val currentDateTime = now.toLocalDateTime(TimeZone.currentSystemDefault())

        // Create target LocalDateTime with current date but specified time
        val targetDateTime = LocalDateTime(
            date = currentDateTime.date,
            time = LocalTime(hour, minute)
        )

        // If the target time is in the past for today, use the specified date
        // Otherwise, use the current date
        val finalDateTime = if (targetDateTime.time < currentDateTime.time) {
            LocalDateTime(
                date = LocalDate(year, month, day),
                time = LocalTime(hour, minute)
            )
        } else {
            targetDateTime
        }

        // Return absolute time in milliseconds
        return finalDateTime.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
    }
}
