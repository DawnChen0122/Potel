package com.example.potel.ui.booking

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@RequiresApi(Build.VERSION_CODES.O)
fun formatMillisToDateString(millis: Long?, pattern: String = "yyyy-MM-dd"): String {
    return if (millis != null) {
        val localDate = Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate()
        localDate.format(DateTimeFormatter.ofPattern(pattern))
    } else {
        ""
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun calculateDateDifference(startDate: String, endDate: String): Long {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val start = LocalDate.parse(startDate, formatter)
    val end = LocalDate.parse(endDate, formatter)
    return ChronoUnit.DAYS.between(start, end)
}

@SuppressLint("NewApi")
@Composable
fun DateSelectionScreen(
    bookingVM: BookingViewModel,
    navController: NavHostController
) {
    val tag = "DateSelectionScreen"

    val order = bookingVM.addOrderEditState.collectAsState().value

    val context = LocalContext.current
    val preferences = context.getSharedPreferences("member", Context.MODE_PRIVATE)
    val memberid = preferences.getString("memberid", "1")!!
    val petid = preferences.getString("petid", "1")!!

    var showDateRangePickerDialog by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("請您選擇入住日期和離開日期！") }

    order.memberid = memberid.toInt()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = message)

//        Button(onClick = { showDateRangePickerDialog = true }) {
//            Text("Start")
//        }
        Button(
            onClick = { showDateRangePickerDialog = true },
            modifier = Modifier
                .height(45.dp) // 高度設定
                .background(Color.Transparent) // 設置背景為透明以避免重疊顏色
                .padding(0.dp), // 確保內部留白為 0
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFAA8066)), // 按鈕背景色
            shape = RoundedCornerShape(8.dp) // 設置圓角
        ) {
            Text(
                text = "Start",
                fontSize = 18.sp,
                color = Color.White // 調整文字顏色
            )
        }

        if (showDateRangePickerDialog) {
            MyDatePickerDialog(
                onConfirm = { pair ->
                    val startDateString = formatMillisToDateString(pair.first, "yyyy-MM-dd")
                    val endDateString = formatMillisToDateString(pair.second, "yyyy-MM-dd")

                    if (startDateString.isNotBlank() && endDateString.isNotBlank()) {
                        val days = calculateDateDifference(startDateString, endDateString)
                        if (days < 0) {
                            message = "Start date must be earlier than end date."
                        } else {
                            bookingVM.setDay(days.toInt())
                            message = """
                                Start date: $startDateString
                                End date: $endDateString
                                Difference: $days days
                            """.trimIndent()
                        }
                    } else {
                        message = "Please select valid dates."
                    }

                    order.expdates = startDateString
                    order.expdatee = endDateString
                    showDateRangePickerDialog = false
                    navController.navigate(BookingScreens.Booking.name)
                },
                onDismiss = {
                    message = "取消"
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

@Preview(showBackground = true)
@Composable
fun pre1(){
    DateSelectionScreen(bookingVM = viewModel(), rememberNavController())
}