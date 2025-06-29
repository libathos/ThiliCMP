package compose.thili.demo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource
import thilicmp.composeapp.generated.resources.Res
import thilicmp.composeapp.generated.resources.button_cancel
import thilicmp.composeapp.generated.resources.button_ok
import thilicmp.composeapp.generated.resources.schedule_mammogram_hour_hint


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCMPTimePicker(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onTimeSelected: (hour: Int, minute: Int) -> Unit,
    initialHour: Int? = null,
    initialMinute: Int? = null,
    is24Hour: Boolean = true
) {
    if (showDialog) {
        val currentTime = remember { Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()) }
        val timePickerState = rememberTimePickerState(
            initialHour = initialHour ?: currentTime.hour,
            initialMinute = initialMinute ?: currentTime.minute,
            is24Hour = is24Hour
        )

        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Surface(
                shape = MaterialTheme.shapes.extraLarge,
                tonalElevation = AlertDialogDefaults.TonalElevation,
                modifier = Modifier
                    .width(IntrinsicSize.Min)
                    .height(IntrinsicSize.Min)
                    .background(
                        shape = MaterialTheme.shapes.extraLarge,
                        color = MaterialTheme.colorScheme.surface
                    ),
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp),
                        text = stringResource(Res.string.schedule_mammogram_hour_hint),
                        style = MaterialTheme.typography.labelMedium,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                    TimePicker(
                        state = timePickerState,
                    )
                    Row(
                        modifier = Modifier
                            .padding(top = 12.dp)
                            .fillMaxWidth()
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        TextButton(onClick = onDismiss) {
                            Text(stringResource(Res.string.button_cancel))
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        TextButton(
                            onClick = {
                                onTimeSelected(timePickerState.hour, timePickerState.minute)
                                onDismiss()
                            }
                        ) {
                            Text(stringResource(Res.string.button_ok))
                        }
                    }
                }
            }
        }
    }
}