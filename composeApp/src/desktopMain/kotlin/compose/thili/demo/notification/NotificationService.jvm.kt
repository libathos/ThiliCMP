@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package compose.thili.demo.notification

import java.awt.Image
import java.awt.SystemTray
import java.awt.Toolkit
import java.awt.TrayIcon
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Timer
import java.util.TimerTask
import javax.swing.SwingUtilities

/**
 * Desktop implementation of NotificationService.
 */
class DesktopNotificationService : NotificationService {
    private val systemTray: SystemTray? = if (SystemTray.isSupported()) SystemTray.getSystemTray() else null
    // Create tray icon
    private val trayIcon: TrayIcon? = if (systemTray != null) {
        try {
            // Use a default icon from the toolkit
            val image: Image = Toolkit.getDefaultToolkit().createImage(javaClass.getResource("/icon.png"))
                ?: Toolkit.getDefaultToolkit().createImage(javaClass.getResource("/icon.jpg"))
                ?: Toolkit.getDefaultToolkit().createImage(javaClass.getResource("/icon.gif"))

            val icon = TrayIcon(image, "ThiliCMP")
            icon.isImageAutoSize = true
            systemTray.add(icon)
            icon
        } catch (e: Exception) {
            println("Could not create tray icon: ${e.message}")
            null
        }
    } else null

    override fun scheduleNotification(
        title: String,
        message: String,
        dateString: String,
        timeString: String,
        notes: String
    ): Boolean {
        try {
            val triggerTimeMillis = parseDateTime(dateString, timeString)
            val currentTimeMillis = System.currentTimeMillis()
            val delayMillis = triggerTimeMillis - currentTimeMillis
            
            if (delayMillis <= 0) {
                // Time has already passed
                return false
            }
            
            // Schedule the notification
            val timer = Timer(true)
            timer.schedule(object : TimerTask() {
                override fun run() {
                    SwingUtilities.invokeLater {
                        showNotification(title, if (notes.isNotEmpty()) "$message\n\n$notes" else message)
                    }
                }
            }, delayMillis)
            
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }
    
    private fun showNotification(title: String, message: String) {
        if (trayIcon != null) {
            trayIcon.displayMessage(title, message, TrayIcon.MessageType.INFO)
        } else {
            // Fallback if system tray is not supported
            println("Notification: $title - $message")
        }
    }
    
    override fun parseDateTime(dateString: String, timeString: String): Long {
        return CommonNotificationUtils.parseDateTime(dateString, timeString)
    }
}

/**
 * Desktop implementation of NotificationServiceFactory.
 */
actual object NotificationServiceFactory {
    private val instance: NotificationService by lazy { DesktopNotificationService() }
    
    actual fun createNotificationService(): NotificationService {
        return instance
    }
}