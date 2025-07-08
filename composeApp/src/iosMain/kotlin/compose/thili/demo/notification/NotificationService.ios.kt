@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package compose.thili.demo.notification

import platform.Foundation.NSDate
import platform.Foundation.NSDateComponents
import platform.Foundation.timeIntervalSince1970
import platform.UserNotifications.UNAuthorizationOptionAlert
import platform.UserNotifications.UNAuthorizationOptionBadge
import platform.UserNotifications.UNAuthorizationOptionSound
import platform.UserNotifications.UNCalendarNotificationTrigger
import platform.UserNotifications.UNMutableNotificationContent
import platform.UserNotifications.UNNotificationRequest
import platform.UserNotifications.UNUserNotificationCenter

/**
 * iOS implementation of NotificationService.
 */
class IOSNotificationService : NotificationService {

    init {
        // Request notification permissions
        UNUserNotificationCenter.currentNotificationCenter().requestAuthorizationWithOptions(
            UNAuthorizationOptionAlert or UNAuthorizationOptionBadge or UNAuthorizationOptionSound
        ) { granted, error ->
            if (granted) {
                println("Notification permission granted")
            } else {
                println("Notification permission denied: ${error?.localizedDescription}")
            }
        }
    }

    override fun scheduleNotification(
        title: String,
        message: String,
        dateString: String,
        timeString: String,
        notes: String
    ): Boolean {
        try {
            // Parse date and time
            val dateParts = dateString.split("/")
            if (dateParts.size != 3) {
                return false
            }

            val day = dateParts[0].toInt()
            val month = dateParts[1].toInt()
            val year = dateParts[2].toInt()

            val timeParts = timeString.split(":")
            if (timeParts.size != 2) {
                return false
            }

            val hour = timeParts[0].toInt()
            val minute = timeParts[1].toInt()

            // Create date components for the trigger
            val dateComponents = NSDateComponents().apply {
                setDay(day.toLong())
                setMonth(month.toLong())
                setYear(year.toLong())
                setHour(hour.toLong())
                setMinute(minute.toLong())
            }

            // Create notification content
            val content = UNMutableNotificationContent().apply {
                setTitle(title)
                setBody(message)
                if (notes.isNotEmpty()) {
                    setSubtitle(notes)
                }
                setSound(platform.UserNotifications.UNNotificationSound.defaultSound)
            }

            // Create calendar trigger
            val trigger = UNCalendarNotificationTrigger.triggerWithDateMatchingComponents(
                dateComponents,
                false // Do not repeat
            )

            // Create request
            val requestIdentifier = "ThiliCMP_Notification_${NSDate().timeIntervalSince1970.toLong()}"
            val request = UNNotificationRequest.requestWithIdentifier(
                requestIdentifier,
                content,
                trigger
            )

            // Add the notification request
            UNUserNotificationCenter.currentNotificationCenter().addNotificationRequest(request) { error ->
                if (error != null) {
                    println("Error scheduling notification: ${error.localizedDescription}")
                }
            }

            return true
        } catch (e: Exception) {
            println("Error scheduling notification: ${e.message}")
            return false
        }
    }

    override fun parseDateTime(dateString: String, timeString: String): Long {
        return CommonNotificationUtils.parseDateTime(dateString, timeString)
    }
}

/**
 * iOS implementation of NotificationServiceFactory.
 */
actual object NotificationServiceFactory {
    private val instance: NotificationService by lazy { IOSNotificationService() }

    actual fun createNotificationService(): NotificationService {
        return instance
    }
}
