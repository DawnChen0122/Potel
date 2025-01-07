package com.example.potel.ui.myorders

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.potel.R
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScreenMOS03(
    navController: NavHostController
    ,memberid: String
) {
    val tag = "ScreenMOS03"
    val backStackEntry = navController.getBackStackEntry(MyOrdersScreens.MOS01.name)
    val myOrdersViewModel: MyOrdersViewModel = viewModel(backStackEntry, key = "myOrdersVM")

    // 是否顯示DateRangerPickerDialog；false代表不顯示
    var showDateRangePickerDialog by remember { mutableStateOf(false) }
    var dateStart by remember { mutableStateOf("") }
    var dateEnd by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()
    var orderlist by remember { mutableStateOf<List<Order>>(emptyList()) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            orderlist = myOrdersViewModel.getOrders(memberid.toInt(), OrderState.CheckedOut.state)
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .background(color = Color(0xFFD9D9D9), shape = RoundedCornerShape(size = 8.dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Bottom
            ) {
                Row {
                    Text(
                        text = "訂房訂單",
                        style = TextStyle(
                            fontSize = 24.sp,
                            lineHeight = 32.sp,
                            fontFamily = FontFamily(Font(R.font.dm_sans)),
                            fontWeight = FontWeight(700),
                            color = Color(0xFF000000),
                            textAlign = TextAlign.Center,
                        ),
                        modifier = Modifier
                            .padding(5.dp)
                    )
                    Text(
                        text = ">歷史訂單",
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 32.sp,
                            fontFamily = FontFamily(Font(R.font.dm_sans)),
                            fontWeight = FontWeight(700),
                            color = Color(0xFF000000),
                            textAlign = TextAlign.Start
                        ),
                        modifier = Modifier
                            .padding(5.dp)
                    )
                }
            }

            if (showDateRangePickerDialog) {
                MyDatePickerDialog(
                    // 確定時會接收到選取日期
                    onConfirm = { pair ->
                        dateStart = formatMillisToDateString(pair.first?:System.currentTimeMillis())
                        dateEnd = formatMillisToDateString(pair.second?:System.currentTimeMillis())
//                        message =
//                            "Start date: ${
//                                pair.first?.let {
//                                    Instant.ofEpochMilli(it).atZone(ZoneId.of("UTC"))
//                                        .toLocalDate().format(ofLocalizedDate(FormatStyle.MEDIUM))
//                                } ?: "no selection"
//                            }\nEnd date: ${
//                                pair.second?.let {
//                                    Instant.ofEpochMilli(it).atZone(ZoneId.of("UTC"))
//                                        .toLocalDate().format(ofLocalizedDate(FormatStyle.MEDIUM))
//                                } ?: "no selection"
//                            }"
                        showDateRangePickerDialog = false
                    },
                    // 設定取消時欲執行內容
                    onDismiss = {
//                        message = "Cancelled"
                        showDateRangePickerDialog = false
                    }
                )
            }


            LazyVerticalGrid(
                columns = GridCells.Fixed(1), // 每列 1 行
                contentPadding = PaddingValues(10.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(orderlist.size) { index ->
                    val order = orderlist[index]

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                border = BorderStroke(width = 1.dp, Color.Black),
                                shape = RoundedCornerShape(10)
                            )
                            .padding(5.dp)
                            .clickable{
                                navController.navigate(route = "${MyOrdersScreens.MOS0301.name}/${order.orderid}")
                            },
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp)
                                .weight(1f)
                        ){
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ){
                                Text(
                                    text = "${order.roomtype.descpt} (${order.orderid})",
                                    fontFamily = FontFamily.SansSerif,
                                    style = TextStyle(
                                        fontWeight = FontWeight(700),
                                        fontSize = 16.sp),
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    text = "金額: ${order.amount}",
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.End
                                )
                            }
                            Text(text = "訂房時間: ${order.createdate}")
                            Row(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                 Text(text = "入住時間: ")
                                 Text(
                                     text = "${order.dates}~${order.datee}"
                                     , fontSize = 14.sp
                                 )
                            }
                            Text(text = "暱稱: ${order.pet.nickname}")
                        }
                        Column (
                            modifier = Modifier
                                .width(70.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            Log.d("ScreenMOS03", "order.pet.imageid=${order.pet.imageid}, composeImageUrl(order.pet.imageid)=${composeImageUrl(order.pet.imageid)}")
                            AsyncImage(
                                model = composeImageUrl(order.pet.imageid),
                                contentDescription = "寵物照片",
                                alignment = Alignment.TopCenter,
                                contentScale = ContentScale.FillWidth,
                                placeholder = painterResource(R.drawable.placeholder)
                            )
//                            Image(
//                                painter = rememberAsyncImagePainter(composeImageUrl(order.pet.imageid)),
//                                contentDescription = "寵物照片",
//                                alignment = Alignment.TopCenter,
//                                modifier = Modifier.fillMaxHeight()
//                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePickerDialog(
    onConfirm: (Pair<Long?, Long?>) -> Unit,
    onDismiss: () -> Unit
) {
    val dateRangePickerState = rememberDateRangePickerState()

    DatePickerDialog(
        // 點擊對話視窗外部或back按鈕時呼叫，並非點擊dismissButton時呼叫
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                // 點擊確定按鈕時呼叫onConfirm(Long?)並將選取日期傳入以回饋給原畫面
                onClick = {
                    onConfirm(
                        Pair(
                            dateRangePickerState.selectedStartDateMillis,
                            dateRangePickerState.selectedEndDateMillis
                        )
                    )
                }
            ) {
                Text("OK")
            }
        },
        // 設定取消按鈕
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DateRangePicker(
            state = dateRangePickerState,
            title = {
                Text(text = "Select date range")
            },
            // 是否要開啟日期輸入模式
            showModeToggle = false,
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .padding(16.dp)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatMillisToDateString(millis: Long): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val date = Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate()
    return date.format(formatter)
}