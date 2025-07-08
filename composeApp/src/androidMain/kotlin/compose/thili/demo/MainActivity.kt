package compose.thili.demo

import android.Manifest
import android.app.Activity
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import compose.thili.demo.notification.AndroidNotificationService
import compose.thili.demo.notification.NotificationServiceFactory
import compose.thili.demo.theme.ThiliTheme
import org.jetbrains.compose.resources.stringResource
import thilicmp.composeapp.generated.resources.Res
import thilicmp.composeapp.generated.resources.button_allow
import thilicmp.composeapp.generated.resources.button_later
import thilicmp.composeapp.generated.resources.notification_dialog_main
import thilicmp.composeapp.generated.resources.notification_dialog_message
import thilicmp.composeapp.generated.resources.notification_dialog_title


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the NotificationServiceFactory
        NotificationServiceFactory.initialize(this)

        setContent {
            MakeStatusBarTransparent()
            ThiliTheme {
                // Get the notification service to check battery optimization status and notification permissions
                val notificationService =
                    NotificationServiceFactory.createNotificationService() as? AndroidNotificationService
                var showBatteryOptimizationDialog by remember { mutableStateOf(false) }
                var showNotificationPermissionDialog by remember { mutableStateOf(false) }

                // Create permission request launcher
                val requestPermissionLauncher = rememberLauncherForActivityResult(
                    ActivityResultContracts.RequestPermission()
                ) { isGranted: Boolean ->
                    // Mark as requested regardless of the result
                    notificationService?.markNotificationPermissionRequested()
                    // Permission dialog is handled, no need to show it anymore
                    showNotificationPermissionDialog = false
                }

                // Check if we need to show the battery optimization dialog or notification permission dialog
                LaunchedEffect(Unit) {
                    if (notificationService != null) {
                        // Check battery optimization
                        if (!notificationService.hasRequestedBatteryOptimizationExemption() &&
                            !notificationService.isIgnoringBatteryOptimizations()
                        ) {
                            showBatteryOptimizationDialog = true
                        }

                        // Check notification permission (only for Android 13+)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            if (!notificationService.hasRequestedNotificationPermission() &&
                                !notificationService.hasNotificationPermission()
                            ) {
                                showNotificationPermissionDialog = true
                            }
                        }
                    }
                }

                // Show the battery optimization dialog if needed
                if (showBatteryOptimizationDialog) {
                    AlertDialog(
                        onDismissRequest = {
                            // Mark as requested even if user dismisses the dialog
                            notificationService?.markBatteryOptimizationExemptionRequested()
                            showBatteryOptimizationDialog = false
                        },
                        title = { Text(stringResource(Res.string.notification_dialog_title)) },
                        text = {
                            Text(
                                stringResource(Res.string.notification_dialog_message),
                                textAlign = TextAlign.Start

                            )
                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    notificationService?.requestBatteryOptimizationExemption()
                                    showBatteryOptimizationDialog = false
                                }
                            ) {
                                Text(stringResource(Res.string.button_allow))
                            }
                        },
                        dismissButton = {
                            Button(
                                onClick = {
                                    notificationService?.markBatteryOptimizationExemptionRequested()
                                    showBatteryOptimizationDialog = false
                                }
                            ) {
                                Text(stringResource(Res.string.button_later))
                            }
                        }
                    )
                }

                // Show the notification permission dialog if needed
                if (showNotificationPermissionDialog && !showBatteryOptimizationDialog) {
                    AlertDialog(
                        onDismissRequest = {
                            // Mark as requested even if user dismisses the dialog
                            notificationService?.markNotificationPermissionRequested()
                            showNotificationPermissionDialog = false
                        },
                        title = { Text(stringResource(Res.string.notification_dialog_main)) },
                        text = {
                            Text(stringResource(Res.string.notification_dialog_message),
                                textAlign = TextAlign.Start
                            )
                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                        // Request the notification permission
                                        requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                                    } else {
                                        // For older versions, just mark as requested
                                        notificationService?.markNotificationPermissionRequested()
                                        showNotificationPermissionDialog = false
                                    }
                                }
                            ) {
                                Text(stringResource(Res.string.button_allow))
                            }
                        },
                        dismissButton = {
                            Button(
                                onClick = {
                                    notificationService?.markNotificationPermissionRequested()
                                    showNotificationPermissionDialog = false
                                }
                            ) {
                                Text(stringResource(Res.string.button_later))
                            }
                        }
                    )
                }

                App()
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}

@Composable
fun Activity.MakeStatusBarTransparent() {
    window.apply {
        val systemUiController = rememberSystemUiController()
        systemUiController.setSystemBarsColor(
            color = Color.White, darkIcons = true
        )
    }
}
