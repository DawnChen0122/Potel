package com.example.potel.ui.booking

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.potel.ui.myorders.MyOrdersViewModel
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ofLocalizedDate
import java.time.format.FormatStyle

@SuppressLint("NewApi")
@Composable
fun DateSelectionScreen(navController: NavHostController) {
    val bookingVM: BookingViewModel = viewModel(key = "bookingVM")

//    val order by bookingVM.addOrderState.collectAsState();
//    order.amount

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
                // 確定時會接收到選取日期
                onConfirm = { pair ->
                    val dateFormatter = ofLocalizedDate(FormatStyle.MEDIUM)
                    message = """
                        Start date: ${
                        pair.first?.let {
                            Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate()
                                .format(dateFormatter)
                        } ?: "no selection"
                    }
                        End date: ${
                        pair.second?.let {
                            Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate()
                                .format(dateFormatter)
                        } ?: "no selection"
                    }
                    """.trimIndent()

                    showDateRangePickerDialog = false
                    navController.navigate("Booking")
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
    // Initialize the date range to display January 2025
    val startMillis = Instant.parse("2025-01-01T00:00:00Z").toEpochMilli()
    val dateRangePickerState = rememberDateRangePickerState(initialDisplayedMonthMillis = startMillis)

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
