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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
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

@OptIn(ExperimentalMaterial3Api::class)
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
    var showSearchText by remember { mutableStateOf(false) }
    var showDateRangePickerDialog by remember { mutableStateOf(false) }
    var isdatestart by remember { mutableStateOf(true) }
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
                Row (
                    verticalAlignment = Alignment.Bottom
                ){
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
                Column(
                    modifier = Modifier
                        .weight(1f),
                    horizontalAlignment = Alignment.End
                ) {
                    Row {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "查詢",
                            modifier = Modifier
                                .clickable {
                                    showSearchText = !showSearchText
                                }
                        )
                    }
                }
            }

            if(showSearchText){
                TextField(
                    value = dateStart,
                    onValueChange = {

                    },
                    readOnly = true,
                    label = {
                        Text(text = "開始日期")
                    },
                    singleLine = true,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "選擇開始日期",
                            modifier = Modifier
                                .clickable {
                                    showDateRangePickerDialog = true
                                    isdatestart = true
                                }
                        )
                    }
                )
                Text(text = " ～ ")
                TextField(
                    value = dateEnd,
                    onValueChange = {

                    },
                    readOnly = true,
                    label = {
                        Text(text = "結束日期")
                    },
                    singleLine = true,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "選擇結束日期",
                            modifier = Modifier
                                .clickable {
                                    showDateRangePickerDialog = true
                                    isdatestart = false
                                }
                        )
                    }
                )
            }

            if(showDateRangePickerDialog){
                MyDatePickerDialog(
                    onDismissRequest = {
                        showDateRangePickerDialog = false
                    },
                    onConfirm = { selectedDate ->
                        if(isdatestart){
                            dateStart = formatMillisToDateString(selectedDate!!)
                        }else{
                            dateEnd = formatMillisToDateString(selectedDate!!)
                        }
                        showDateRangePickerDialog = false
                    },
                    onDismiss = {
                        showDateRangePickerDialog = false
                    },
                    title = if(isdatestart) "選擇開始日期" else "選擇結束日期"
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
                            .clickable {
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


@RequiresApi(Build.VERSION_CODES.O)
fun formatMillisToDateString(millis: Long): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val date = Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate()
    return date.format(formatter)
}

// 使用的DatePicker屬於androidx.compose.material3測試功能，需要加上"@OptIn"註記
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePickerDialog(
    onDismissRequest: () -> Unit,
    onConfirm: (Long?) -> Unit,
    onDismiss: () -> Unit,
    title: String
) {
    val datePickerState = rememberDatePickerState(
        // SelectableDates介面用來限制可選擇的日期與年
        selectableDates = object : SelectableDates {
            // 將顯示的日期逐一傳給utcTimeMillis參數，回傳true代表該日可選；false代表該日不可選
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return true
            }

            // 將顯示的年逐一傳給year參數，回傳true代表該年可選；false代表該年不可選
            override fun isSelectableYear(year: Int): Boolean {
                return true
            }
        }
    )

    DatePickerDialog(
        // 點擊對話視窗外部或back按鈕時呼叫，並非點擊dismissButton時呼叫
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(
                // 點擊確定按鈕時呼叫onConfirm(Long?)並將選取日期傳入以回饋給原畫面
                onClick = {
                    onConfirm(datePickerState.selectedDateMillis)
                }
            ) {
                Text("確定")
            }
        },
        // 設定取消按鈕
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("取消")
            }
        }
    ) {
        DatePicker(
            state = datePickerState,
            title = {
                Text(text = title)
            }
        )
    }
}