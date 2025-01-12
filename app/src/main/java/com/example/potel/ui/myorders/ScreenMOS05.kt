package com.example.potel.ui.myorders

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.potel.R
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScreenMOS05(
    navController: NavHostController
    ,memberid: String
) {
    val tag = "ScreenMOS05"
    val backStackEntry = navController.getBackStackEntry(MyOrdersScreens.MOS01.name)
    val myOrdersViewModel: MyOrdersViewModel = viewModel(backStackEntry, key = "myOrdersVM")

    // 是否顯示DateRangerPickerDialog；false代表不顯示
    var showSearchText by remember { mutableStateOf(false) }
    var showDateRangePickerDialog by remember { mutableStateOf(false) }
    var isdatestart by remember { mutableStateOf(true) }
    var dateStart by remember { mutableStateOf("") }
    var dateEnd by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()
    var prdorderlist by remember { mutableStateOf<List<PrdOrder>>(emptyList()) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val responseObject = myOrdersViewModel.getPrdOrders(memberid.toInt(), 'A')
            prdorderlist = if(responseObject.respcode==0){
                responseObject.resobj as List<PrdOrder>
            }else{
                emptyList()
            }
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
                .background(color = Color(0xFFD9D9D9), shape = RoundedCornerShape(size = 8.dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row (
                    modifier = Modifier
                        .weight(0.9f),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "購物訂單",
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
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "查詢",
                    modifier = Modifier
                        .weight(0.1f)
                        .combinedClickable(
                            onClick = {
                                coroutineScope.launch {
                                    val responseObject = myOrdersViewModel.getPrdOrders(memberid.toInt(), 'A', dateStart, dateEnd)
                                    prdorderlist = if(responseObject.respcode==0){
                                        responseObject.resobj as List<PrdOrder>
                                    }else{
                                        emptyList()
                                    }
                                }
                            },
                            onLongClick = {
                                showSearchText = !showSearchText
                            },
                        )

                )
            }

            if(showSearchText){
                Row(
                    horizontalArrangement = Arrangement.End
                ) {
                    TextField(
                        modifier = Modifier.weight(0.45f),
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
                    Text(
                        modifier = Modifier.weight(0.1f),
                        text = " ～ "
                    )
                    TextField(
                        modifier = Modifier.weight(0.45f),
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
                items(prdorderlist.size) { index ->
                    val prdorder = prdorderlist[index]

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                border = BorderStroke(width = 1.dp, Color.Black),
                                shape = RoundedCornerShape(5)
                            )
                            .padding(5.dp)
                            .clickable {
                                navController.navigate(route = "${MyOrdersScreens.MOS0501.name}/${prdorder.prdorderid}")
                            },
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        )
                        {
                            Text(text = "訂單編號: ${prdorder.prdorderid}",
                                fontFamily = FontFamily.SansSerif,
                                style = TextStyle(fontWeight = FontWeight(700),
                                    fontSize = 18.sp),
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = prdorder.createdate,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.End
                            )
                        }
                        Row { Text(text = "訂單金額: NTD ${prdorder.amount}") }
                    }
                }
            }
        }
    }
}
