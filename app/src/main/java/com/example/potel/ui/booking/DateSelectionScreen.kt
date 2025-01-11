package com.example.potel.ui.booking

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@RequiresApi(Build.VERSION_CODES.O)
fun formatLongToDateString(timestamp: Long, pattern: String = "yyyy-MM-dd"): String {
    val formatter = DateTimeFormatter.ofPattern(pattern).withZone(ZoneId.systemDefault())
    return formatter.format(Instant.ofEpochMilli(timestamp))
}

@RequiresApi(Build.VERSION_CODES.O)
fun calculateDateDifference(startDate: String, endDate: String): Long {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val start = Instant.parse("${startDate}T00:00:00Z").atZone(ZoneId.systemDefault()).toLocalDate()
    val end = Instant.parse("${endDate}T00:00:00Z").atZone(ZoneId.systemDefault()).toLocalDate()
    return ChronoUnit.DAYS.between(start, end)
}

@SuppressLint("NewApi")
@Composable
fun DateSelectionScreen(
    bookingVM: BookingViewModel,
    navController: NavHostController
) {
    val order = bookingVM.addOrderEditState.collectAsState().value

    var showDateRangePickerDialog by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("請您選取入住日期和離開日期！") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = message)

        Button(onClick = { showDateRangePickerDialog = true }) {
            Text("Start")
        }

        if (showDateRangePickerDialog) {
            MyDatePickerDialog(
                onConfirm = { pair ->
                    val startDateString =
                        pair.first?.let { formatLongToDateString(it, "yyyy-MM-dd") } ?: ""
                    val endDateString =
                        pair.second?.let { formatLongToDateString(it, "yyyy-MM-dd") } ?: ""

                    val dateDifference =
                        if (startDateString.isNotBlank() && endDateString.isNotBlank()) {
                            val days = calculateDateDifference(startDateString, endDateString)
                            bookingVM.setDay(days.toInt())
                            Log.d("choose day ", days.toString())
                        } else {
                            0L // 若未選日期，則返回0天
                        }

                    message = """
                        Start date: $startDateString
                        End date: $endDateString
                        Difference: $dateDifference days
                    """.trimIndent()

                    order.expdates = startDateString
                    order.expdatee = endDateString
                    showDateRangePickerDialog = false
                    navController.navigate(BookingScreens.Booking.name)
                },
                onDismiss = {
                    message = "Cancelled"
                    showDateRangePickerDialog = false
                }
            )
        }
    }
}

@SuppressLint("NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePickerDialog(onConfirm: (Pair<Long?, Long?>) -> Unit, onDismiss: () -> Unit) {
    val startMillis = Instant.parse("2025-01-01T00:00:00Z").toEpochMilli()
    val dateRangePickerState =
        rememberDateRangePickerState(initialDisplayedMonthMillis = startMillis)

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                onConfirm(
                    Pair(
                        dateRangePickerState.selectedStartDateMillis,
                        dateRangePickerState.selectedEndDateMillis
                    )
                )
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DateRangePicker(
            state = dateRangePickerState,
            title = { Text("Select date range") },
            showModeToggle = false,
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .padding(16.dp)
        )
    }
}
