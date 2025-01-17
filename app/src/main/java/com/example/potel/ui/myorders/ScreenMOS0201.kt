package com.example.potel.ui.myorders

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
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
import coil.compose.rememberAsyncImagePainter
import com.example.potel.R
import com.example.potel.ui.theme.TipColor
import kotlinx.coroutines.launch

@Composable
fun ScreenMOS0201(
    navController: NavHostController
    ,orderid: String
) {
    val backStackEntry = navController.getBackStackEntry(MyOrdersScreens.MOS01.name)
    val myOrdersViewModel: MyOrdersViewModel = viewModel(backStackEntry, key = "myOrdersVM")

    val coroutineScope = rememberCoroutineScope()
    var order by remember { mutableStateOf<Order?>(null) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            order = myOrdersViewModel.getOrder(orderid.toInt())
            myOrdersViewModel.setOrder(order)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(2.dp)
            .background(color = TipColor.deep_brown),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
//                .padding(10.dp)
//                .background(color = Color(0xFFD9D9D9), shape = RoundedCornerShape(size = 8.dp)),
                .background(color = TipColor.light_brown,
            shape = RoundedCornerShape(size = 8.dp)),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "訂房訂單",
                    style = TextStyle(
                        fontSize = 24.sp,
                        lineHeight = 32.sp,
                        fontFamily = FontFamily(Font(R.font.dm_sans)),
                        fontWeight = FontWeight(700),
//                        color = Color(0xFF000000),
                        color = TipColor.deep_brown,
                        textAlign = TextAlign.Center,
                    ),
                    modifier = Modifier
                        .padding(5.dp)
                )
                Text(
                    text = ">預約訂單明細",
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 32.sp,
                        fontFamily = FontFamily(Font(R.font.dm_sans)),
                        fontWeight = FontWeight(700),
//                        color = Color(0xFF000000),
                        color = TipColor.deep_brown,
                        textAlign = TextAlign.Start
                    ),
                    modifier = Modifier
                        .padding(5.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween // 讓內容分佈在兩端
            ) {
                Image(
                    painter = rememberAsyncImagePainter(composeImageUrl(order?.roomtype?.imageid?.toInt()?:0)),
                    contentDescription = "房間照片"
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween // 讓內容分佈在兩端
            ) {
                Text(
                    text = order?.roomtype?.descpt?:"No data",
                    style = TextStyle(
                        fontSize = 22.sp,
                        lineHeight = 32.sp,
                        fontFamily = FontFamily(Font(R.font.dm_sans)),
                        fontWeight = FontWeight(700),
//                        color = Color(0xFF000000),
                        color = TipColor.deep_brown,
                        textAlign = TextAlign.Start
                    ),
                    modifier = Modifier
                        .padding(8.dp)
                )
                OutlinedButton(
                    onClick = {
                        navController.navigate(route = MyOrdersScreens.MOS0202.name)
                    },
                    shape = RoundedCornerShape(20),
                    border = BorderStroke(width = 1.dp, color = Color.Black),
                    contentPadding = PaddingValues(5.dp)
                ) {
                    Text(
                        text = "取消訂單",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.dm_sans)),
                            fontWeight = FontWeight(700),
//                        color = Color(0xFF000000),
                            color = TipColor.deep_brown,
                        ),
                    )
                }
            }
            HorizontalDivider()
            Text(
                text = "訂房時間: ${order?.createdate}",
                style = TextStyle(
                    fontSize = 18.sp,
                    lineHeight = 32.sp,
                    fontFamily = FontFamily(Font(R.font.dm_sans)),
//                    fontWeight = FontWeight(700),
//                        color = Color(0xFF000000),
                    color = TipColor.deep_brown,
                    textAlign = TextAlign.Start
                ),
                modifier = Modifier
                    .padding(8.dp)
            )
            Text(
                text = "預約入住時間:",
                style = TextStyle(
                    fontSize = 18.sp,
                    lineHeight = 32.sp,
                    fontFamily = FontFamily(Font(R.font.dm_sans)),
//                    fontWeight = FontWeight(700),
//                        color = Color(0xFF000000),
                    color = TipColor.deep_brown,
                    textAlign = TextAlign.Start
                ),
                modifier = Modifier
                    .padding(8.dp)
            )
            Text(
                text = "   ${order?.expdates} ~ ${order?.expdatee}",
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 32.sp,
                    fontFamily = FontFamily(Font(R.font.dm_sans)),
//                    fontWeight = FontWeight(700),
//                        color = Color(0xFF000000),
                    color = TipColor.deep_brown,
                    textAlign = TextAlign.Start
                ),
                modifier = Modifier
                    .padding(8.dp)
            )
            Text(
                text = "訂單金額: NTD\$ ${order?.amount}",
                style = TextStyle(
                    fontSize = 18.sp,
                    lineHeight = 32.sp,
                    fontFamily = FontFamily(Font(R.font.dm_sans)),
//                    fontWeight = FontWeight(700),
//                        color = Color(0xFF000000),
                    color = TipColor.deep_brown,
                    textAlign = TextAlign.Start
                ),
                modifier = Modifier
                    .padding(8.dp)
            )
            Text(
                text = "訂單編號: ${order?.orderid}",
                style = TextStyle(
                    fontSize = 18.sp,
                    lineHeight = 32.sp,
                    fontFamily = FontFamily(Font(R.font.dm_sans)),
//                    fontWeight = FontWeight(700),
//                        color = Color(0xFF000000),
                    color = TipColor.deep_brown,
                    textAlign = TextAlign.Start
                ),
                modifier = Modifier
                    .padding(8.dp)
            )
            Text(
                text = "寵物暱稱: ${order?.pet?.nickname}",
                style = TextStyle(
                    fontSize = 18.sp,
                    lineHeight = 32.sp,
                    fontFamily = FontFamily(Font(R.font.dm_sans)),
//                    fontWeight = FontWeight(700),
//                        color = Color(0xFF000000),
                    color = TipColor.deep_brown,
                    textAlign = TextAlign.Start
                ),
                modifier = Modifier
                    .padding(8.dp)
            )

        }
    }
}