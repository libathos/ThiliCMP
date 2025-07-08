@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package compose.thili.demo.notification

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.content.edit

/**
 * Android implementation of NotificationService.
 */
class AndroidNotificationService(private val context: Context) : NotificationService {
    companion object {
        const val CHANNEL_ID = "ThiliCMP_Notifications"
        private const val NOTIFICATION_ID_COUNTER_KEY = "notification_id_counter"
        private const val EXACT_ALARM_PERMISSION_REQUESTED_KEY = "exact_alarm_permission_requested"
        private const val BATTERY_OPTIMIZATION_REQUESTED_KEY = "battery_optimization_requested"
        private const val NOTIFICATIONS_ENABLED_KEY = "notifications_enabled"
        private const val NOTIFICATION_PERMISSION_REQUESTED_KEY =
            "notification_permission_requested"
    }

    private fun getUniqueNotificationId(): Int {
        val sharedPrefs = context.getSharedPreferences("notification_prefs", Context.MODE_PRIVATE)
        val currentId = sharedPrefs.getInt(NOTIFICATION_ID_COUNTER_KEY, 1001)
        val nextId = currentId + 1
        sharedPrefs.edit { putInt(NOTIFICATION_ID_COUNTER_KEY, nextId) }
        return currentId
    }

    init {
        createNotificationChannel()

        // Enable notifications by default
        val sharedPrefs = context.getSharedPreferences("notification_prefs", Context.MODE_PRIVATE)
        if (!sharedPrefs.contains(NOTIFICATIONS_ENABLED_KEY)) {
            sharedPrefs.edit { putBoolean(NOTIFICATIONS_ENABLED_KEY, true) }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "ThiliCMP Notifications"
            val descriptionText = "Notifications for ThiliCMP app"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    /**
     * Checks if the app has permission to schedule exact alarms.
     * Only relevant for Android S (API 31) and above.
     */
    private fun canScheduleExactAlarms(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.canScheduleExactAlarms()
        } else {
            // For versions below Android S, we can always schedule exact alarms
            true
        }
    }

    /**
     * Checks if we've already requested the exact alarm permission.
     */
    private fun hasRequestedExactAlarmPermission(): Boolean {
        val sharedPrefs = context.getSharedPreferences("notification_prefs", Context.MODE_PRIVATE)
        return sharedPrefs.getBoolean(EXACT_ALARM_PERMISSION_REQUESTED_KEY, false)
    }

    /**
     * Marks that we've requested the exact alarm permission.
     */
    private fun markExactAlarmPermissionRequested() {
        val sharedPrefs = context.getSharedPreferences("notification_prefs", Context.MODE_PRIVATE)
        sharedPrefs.edit { putBoolean(EXACT_ALARM_PERMISSION_REQUESTED_KEY, true) }
    }

    /**
     * Opens the system settings to allow the user to grant permission for scheduling exact alarms.
     * Returns true if we were able to open the settings, false otherwise.
     */
    private fun requestExactAlarmPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            try {
                // Mark that we've requested the permission
                markExactAlarmPermissionRequested()

                // Create intent to open the exact alarm permission settings
                val intent = Intent().apply {
                    action = Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
                    data = Uri.fromParts("package", context.packageName, null)
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }

                // Start the settings activity
                context.startActivity(intent)
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        } else {
            // For versions below Android S, we don't need to request this permission
            true
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
            val triggerTimeMillis = parseDateTime(dateString, timeString)

            // Get a unique ID for this notification
            val notificationId = getUniqueNotificationId()

            // Create intent for notification receiver
            val intent = Intent(context, NotificationReceiver::class.java).apply {
                putExtra("NOTIFICATION_TITLE", title)
                putExtra("NOTIFICATION_MESSAGE", message)
                putExtra("NOTIFICATION_NOTES", notes)
                putExtra("NOTIFICATION_ID", notificationId)
            }

            // Create pending intent for broadcast receiver
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                notificationId,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            // Create notification
            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setLargeIcon(BitmapFactory.decodeStream(context.assets.open("info.webp")))
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

            if (notes.isNotEmpty()) {
                builder.setStyle(NotificationCompat.BigTextStyle().bigText("$message\n\n$notes"))
            }

            // Schedule notification
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            try {
                // Check if we can schedule exact alarms on Android S and above
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    if (canScheduleExactAlarms()) {
                        // We have permission, schedule exact alarm
                        scheduleExactAlarm(alarmManager, triggerTimeMillis, pendingIntent)
                    } else {
                        // We don't have permission, check if we should request it
                        if (!hasRequestedExactAlarmPermission()) {
                            // Request permission to schedule exact alarms
                            val permissionRequested = requestExactAlarmPermission()

                            if (permissionRequested) {
                                // We've shown the permission request, but we still need to set an inexact alarm
                                // for this notification since the user hasn't granted permission yet
                                alarmManager.set(
                                    AlarmManager.RTC_WAKEUP,
                                    triggerTimeMillis,
                                    pendingIntent
                                )
                            } else {
                                // Failed to show permission request, fall back to inexact alarm
                                alarmManager.set(
                                    AlarmManager.RTC_WAKEUP,
                                    triggerTimeMillis,
                                    pendingIntent
                                )
                            }
                        } else {
                            // We've already requested permission before, fall back to inexact alarm
                            alarmManager.set(
                                AlarmManager.RTC_WAKEUP,
                                triggerTimeMillis,
                                pendingIntent
                            )
                        }
                    }
                } else {
                    // For versions below Android S, we can schedule exact alarms
                    scheduleExactAlarm(alarmManager, triggerTimeMillis, pendingIntent)
                }
            } catch (e: SecurityException) {
                // Handle the case where we don't have permission
                alarmManager.set(
                    AlarmManager.RTC_WAKEUP,
                    triggerTimeMillis,
                    pendingIntent
                )
            }

            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    override fun parseDateTime(dateString: String, timeString: String): Long {
        return CommonNotificationUtils.parseDateTime(dateString, timeString)
    }

    /**
     * Helper method to schedule an exact alarm based on Android version.
     */
    private fun scheduleExactAlarm(
        alarmManager: AlarmManager,
        triggerTimeMillis: Long,
        pendingIntent: PendingIntent
    ) {
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerTimeMillis,
            pendingIntent
        )
    }

    /**
     * Checks if the app is on the battery optimization whitelist.
     * Only relevant for Android M (API 23) and above.
     */
    fun isIgnoringBatteryOptimizations(): Boolean {
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        return powerManager.isIgnoringBatteryOptimizations(context.packageName)
    }

    /**
     * Checks if we've already requested the battery optimization exemption.
     */
    fun hasRequestedBatteryOptimizationExemption(): Boolean {
        val sharedPrefs = context.getSharedPreferences("notification_prefs", Context.MODE_PRIVATE)
        return sharedPrefs.getBoolean(BATTERY_OPTIMIZATION_REQUESTED_KEY, false)
    }

    /**
     * Marks that we've requested the battery optimization exemption.
     */
    fun markBatteryOptimizationExemptionRequested() {
        val sharedPrefs = context.getSharedPreferences("notification_prefs", Context.MODE_PRIVATE)
        sharedPrefs.edit { putBoolean(BATTERY_OPTIMIZATION_REQUESTED_KEY, true) }
    }

    /**
     * Opens the system settings to allow the user to disable battery optimization for the app.
     * Returns true if we were able to open the settings, false otherwise.
     */
    @SuppressLint("BatteryLife")
    fun requestBatteryOptimizationExemption() {
        try {
            // Mark that we've requested the exemption
            markBatteryOptimizationExemptionRequested()

            // Create intent to open battery optimization settings
            val intent = Intent().apply {
                action = Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
                data = Uri.fromParts("package", context.packageName, null)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }

            // Start the settings activity
            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * Checks if notification permission is granted.
     * Only relevant for Android 13 (API 33) and above.
     */
    fun hasNotificationPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            // For versions below Android 13, notification permission is granted by default
            true
        }
    }

    /**
     * Checks if we've already requested notification permission.
     */
    fun hasRequestedNotificationPermission(): Boolean {
        val sharedPrefs = context.getSharedPreferences("notification_prefs", Context.MODE_PRIVATE)
        return sharedPrefs.getBoolean(NOTIFICATION_PERMISSION_REQUESTED_KEY, false)
    }

    /**
     * Marks that we've requested notification permission.
     */
    fun markNotificationPermissionRequested() {
        val sharedPrefs = context.getSharedPreferences("notification_prefs", Context.MODE_PRIVATE)
        sharedPrefs.edit { putBoolean(NOTIFICATION_PERMISSION_REQUESTED_KEY, true) }
    }
}

/**
 * Android implementation of NotificationServiceFactory.
 */
actual object NotificationServiceFactory {
    private var instance: NotificationService? = null

    actual fun createNotificationService(): NotificationService {
        if (instance == null) {
            // This will be initialized in MainActivity
            throw IllegalStateException("NotificationService not initialized. Call initialize() first.")
        }
        return instance!!
    }

    fun initialize(context: Context) {
        if (instance == null) {
            instance = AndroidNotificationService(context)
        }
    }
}

/**
 * Broadcast receiver for showing notifications.
 */
class NotificationReceiver : android.content.BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val title = intent.getStringExtra("NOTIFICATION_TITLE") ?: "ThiliCMP Reminder"
        val message = intent.getStringExtra("NOTIFICATION_MESSAGE") ?: "You have a reminder"
        val notes = intent.getStringExtra("NOTIFICATION_NOTES") ?: ""

        // Get the notification ID from the intent, or generate a unique one if not provided
        val notificationId = if (intent.hasExtra("NOTIFICATION_ID")) {
            intent.getIntExtra("NOTIFICATION_ID", 0)
        } else {
            // Generate a unique ID based on current time if not provided
            System.currentTimeMillis().toInt()
        }

        val builder = NotificationCompat.Builder(context, AndroidNotificationService.CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setLargeIcon(BitmapFactory.decodeStream(context.assets.open("info.webp")))
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        if (notes.isNotEmpty()) {
            builder.setStyle(NotificationCompat.BigTextStyle().bigText("$message\n\n$notes"))
        }

        notificationManager.notify(notificationId, builder.build())
    }
}
