package compose.thili.demo.fourthscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.thili.demo.ThiliScreens
import compose.thili.demo.notification.NotificationServiceFactory
import compose.thili.demo.ui.components.MyCMPDatePicker
import compose.thili.demo.ui.components.MyCMPTimePicker
import compose.thili.demo.ui.components.ThiliAppBar
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import thilicmp.composeapp.generated.resources.Res
import thilicmp.composeapp.generated.resources.back
import thilicmp.composeapp.generated.resources.profile_save
import thilicmp.composeapp.generated.resources.schedule_mammogram_date
import thilicmp.composeapp.generated.resources.schedule_mammogram_date_hint
import thilicmp.composeapp.generated.resources.schedule_mammogram_hour
import thilicmp.composeapp.generated.resources.schedule_mammogram_hour_hint
import thilicmp.composeapp.generated.resources.schedule_mammogram_notes
import thilicmp.composeapp.generated.resources.schedule_mammogram_notes_hint
import thilicmp.composeapp.generated.resources.schedule_mammogram_title

@Composable
fun FourthScreen(
    currentScreen: ThiliScreens,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }

    var time by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }

    var showDatePickerPopup by remember { mutableStateOf(false) }
    var showTimePickerPopup by remember { mutableStateOf(false) }

    // Store the screen title for use in notification
    val screenTitle = currentScreen.title?.let { stringResource(it) } ?: "ThiliCMP"

    MyCMPDatePicker(
        showDialog = showDatePickerPopup,
        onDismiss = { showDatePickerPopup = false },
        onDateSelected = { formattedDate ->
            date = formattedDate
        }
    )

    MyCMPTimePicker(
        showDialog = showTimePickerPopup,
        onDismiss = { showTimePickerPopup = false },
        onTimeSelected = { hour, minute ->
            time = "${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}"
        },
        is24Hour = true
    )

    Scaffold(topBar = {
        ThiliAppBar(
            canNavigateBack = canNavigateBack,
            navigateUp = navigateUp,
            currentScreen = currentScreen
        )
    }) { innerPadding ->

        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {

            Box(
                modifier = Modifier.matchParentSize().paint(
                    painterResource(Res.drawable.back), contentScale = ContentScale.FillBounds
                )
            ) {


                Box(Modifier.padding(20.dp)) {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                            .wrapContentHeight(Alignment.CenterVertically)
                            .border(2.dp, Color(0xFFFA5E73)).align(Alignment.TopCenter)
                            .background(Color.White), contentAlignment = Alignment.CenterStart
                    ) {
                        Column(
                            Modifier.padding(
                                start = 20.dp, top = 15.dp, end = 10.dp, bottom = 15.dp
                            ).verticalScroll(rememberScrollState())
                        ) {
                            Text(
                                text = stringResource(Res.string.schedule_mammogram_title),
                                style = TextStyle(fontSize = 16.sp, color = Color.Black),
                                modifier = Modifier.padding(top = 15.dp)
                            )
                            OutlinedTextField(
                                value = title,
                                onValueChange = { title = it },
                                modifier = Modifier.fillMaxWidth(),
                                textStyle = TextStyle(fontSize = 14.sp, color = Color.Black),
                                singleLine = true,
                                readOnly = true,
                                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                                    imeAction = ImeAction.Go
                                ),
                                placeholder = { Text(text = stringResource(currentScreen.title!!)) })
                            Spacer(
                                modifier = Modifier.height(2.dp).fillMaxWidth()
                                    .background(Color(0xFFc7d2e8))
                            )
                            Text(
                                text = stringResource(Res.string.schedule_mammogram_date),
                                style = TextStyle(fontSize = 16.sp, color = Color.Black),
                                modifier = Modifier.padding(top = 20.dp)
                            )
                            OutlinedTextField(
                                value = date,
                                onValueChange = { date = it },
                                modifier = Modifier.fillMaxWidth()
                                    .clickable {
                                        showDatePickerPopup = true
                                    },
                                textStyle = TextStyle(fontSize = 14.sp, color = Color.Black),
                                singleLine = true,
                                readOnly = true,
                                enabled = false,
                                placeholder = { Text(text = stringResource(Res.string.schedule_mammogram_date_hint)) },
                                colors = OutlinedTextFieldDefaults.colors(
                                    disabledTextColor = MaterialTheme.colorScheme.onSurface,
                                    disabledContainerColor = Color.Transparent,
                                    disabledBorderColor = MaterialTheme.colorScheme.outline,
                                    disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    disabledSupportingTextColor = MaterialTheme.colorScheme.onSurfaceVariant,

                                    )
                            )
                            Spacer(
                                modifier = Modifier.height(2.dp).fillMaxWidth()
                                    .background(Color(0xFFc7d2e8))
                            )
                            Text(
                                text = stringResource(Res.string.schedule_mammogram_hour),
                                style = TextStyle(fontSize = 16.sp, color = Color.Black),
                                modifier = Modifier.padding(top = 20.dp)
                            )
                            OutlinedTextField(
                                value = time,
                                onValueChange = { time = it },
                                modifier = Modifier.fillMaxWidth()
                                    .clickable {
                                        showTimePickerPopup = true
                                    },
                                textStyle = TextStyle(fontSize = 14.sp, color = Color.Black),
                                readOnly = true,
                                enabled = false,
                                placeholder = { Text(text = stringResource(Res.string.schedule_mammogram_hour_hint)) },
                                colors = OutlinedTextFieldDefaults.colors(
                                    disabledTextColor = MaterialTheme.colorScheme.onSurface,
                                    disabledContainerColor = Color.Transparent,
                                    disabledBorderColor = MaterialTheme.colorScheme.outline,
                                    disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    disabledSupportingTextColor = MaterialTheme.colorScheme.onSurfaceVariant,

                                    )
                            )
                            Spacer(
                                modifier = Modifier.height(2.dp).fillMaxWidth()
                                    .background(Color(0xFFc7d2e8))
                            )
                            Text(
                                text = stringResource(Res.string.schedule_mammogram_notes),
                                style = TextStyle(fontSize = 16.sp, color = Color.Black),
                                modifier = Modifier.padding(top = 20.dp)
                            )
                            OutlinedTextField(
                                value = note,
                                onValueChange = { note = it },
                                modifier = Modifier.fillMaxWidth(),
                                textStyle = TextStyle(fontSize = 14.sp, color = Color.Black),
                                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                                    imeAction = ImeAction.Done
                                ),
                                placeholder = { Text(text = stringResource(Res.string.schedule_mammogram_notes_hint)) })
                            Spacer(
                                modifier = Modifier.height(2.dp).fillMaxWidth()
                                    .background(Color(0xFFc7d2e8))
                            )

                            TextButton(
                                onClick = {
                                    // Schedule notification if date and time are selected
                                    if (date.isNotEmpty() && time.isNotEmpty()) {
                                        val notificationService = NotificationServiceFactory.createNotificationService()
                                        val notificationTitle = title.ifEmpty { screenTitle }

                                        val success = notificationService.scheduleNotification(
                                            title = notificationTitle,
                                            message = note,
                                            dateString = date,
                                            timeString = time
                                        )

                                        if (success) {
                                            println("Notification scheduled successfully for $date at $time")
                                        } else {
                                            println("Failed to schedule notification")
                                        }
                                    }
                                    navigateUp()
                                },
                                modifier = Modifier.fillMaxWidth()
                                    .padding(horizontal = 10.dp, vertical = 50.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Color(0xFFFA5E73))
                            ) {
                                Text(
                                    text = stringResource(Res.string.profile_save).uppercase(),
                                    color = Color.White
                                )
                            }

                        }
                    }
                }
            }
        }
    }
}
