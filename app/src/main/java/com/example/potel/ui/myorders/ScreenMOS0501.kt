package com.example.potel.ui.myorders

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
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

@Composable
fun ScreenMOS0501(
    navController: NavHostController,
    prdorderid: String
) {
    val tag = "ScreenMOS0401"
    val backStackEntry = navController.getBackStackEntry(MyOrdersScreens.MOS01.name)
    val myOrdersViewModel: MyOrdersViewModel = viewModel(backStackEntry, key = "myOrdersVM")

    val coroutineScope = rememberCoroutineScope()
    var prdorder by remember { mutableStateOf<PrdOrder?>(null) }
    var prdorditems by remember { mutableStateOf<List<PrdOrdItem>>(emptyList()) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val ro = myOrdersViewModel.getPrdOrder(prdorderid.toInt())
            prdorder = if (ro.respcode == 0) {
                ro.resobj
            }else{
                null
            }
            myOrdersViewModel.setPrdOrder(prdorder)
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
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Bottom
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
                    text = ">歷史訂單明細",
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

            Text(text = "訂單編號: ${prdorder?.prdorderid}",
                fontFamily = FontFamily.SansSerif,
                style = TextStyle(fontWeight = FontWeight(700),
                    fontSize = 18.sp)
            )
            Text(text = "訂單金額: NTD ${prdorder?.amount}")
            Text(text = "交易日期: ${prdorder?.createdate}")

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider()
            Text(text = "訂單內容:",
                fontFamily = FontFamily.SansSerif,
                style = TextStyle(fontWeight = FontWeight(700),
                    fontSize = 18.sp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(1), // 每列 1 行
                contentPadding = PaddingValues(10.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                prdorditems = prdorder?.prdorditems?: emptyList()
                items(prdorditems.size) { index ->
                    val prdorditem = prdorditems[index]

                    // 一筆資料
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                border = BorderStroke(width = 1.dp, Color.Black),
                                shape = RoundedCornerShape(5)
                            )
                            .padding(5.dp),
                    ) {
                        Text(text = "品名: ${prdorditem.product.prdname}")
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        )
                        {
                            Text(
                                text = "金額: NTD ${prdorditem.product.price}",
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Start
                            )
                            Text(
                                text = "數量: ${prdorditem.prdcount}",
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.End
                            )
                        }
                    }
                }
            }
        }
    }
}