package compose.thili.demo.ui.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.datetime.Clock.*
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource
import thilicmp.composeapp.generated.resources.Res
import thilicmp.composeapp.generated.resources.button_cancel
import thilicmp.composeapp.generated.resources.button_ok


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCMPDatePicker(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onDateSelected: (String) -> Unit
) {

    val todayUtcMillisAtStartOfDay = remember {
        val now = System.now()
        val todayUtc = now.toLocalDateTime(TimeZone.UTC).date
        todayUtc.atStartOfDayIn(TimeZone.UTC).toEpochMilliseconds()
    }

    val datePickerState = rememberDatePickerState(
        yearRange = (System.now().toLocalDateTime(TimeZone.currentSystemDefault()).year..System.now().toLocalDateTime(TimeZone.currentSystemDefault()).year + 100),
        initialSelectedDateMillis = todayUtcMillisAtStartOfDay,
        selectableDates =object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis >= todayUtcMillisAtStartOfDay
            }
        }
    )


    if (showDialog) {
        DatePickerDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val selectedInstant = Instant.fromEpochMilliseconds(millis)
                            onDateSelected(selectedInstant.toFormattedDateString())
                        }
                        onDismiss()
                    },
                    enabled = datePickerState.selectedDateMillis != null
                ) {
                    Text(stringResource(Res.string.button_ok))
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text(stringResource(Res.string.button_cancel))
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

fun Instant.toFormattedDateString(timeZone: TimeZone = TimeZone.UTC): String {
    val localDateTime = this.toLocalDateTime(timeZone)
    return "${localDateTime.dayOfMonth.toString().padStart(2, '0')}/" +
            "${localDateTime.monthNumber.toString().padStart(2, '0')}/" +
            "${localDateTime.year}"
}
