package com.example.potel.ui.myorders

import android.util.Log
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.potel.ui.theme.TipColor
import kotlinx.coroutines.launch

@Composable
fun ScreenMOS04(
    navController: NavHostController
    ,memberid: String
) {
    val tag = "ScreenMOS04"
    val backStackEntry = navController.getBackStackEntry(MyOrdersScreens.MOS01.name)
    val myOrdersViewModel: MyOrdersViewModel = viewModel(backStackEntry, key = "myOrdersVM")

    val coroutineScope = rememberCoroutineScope()
    var prdorderlist by remember { mutableStateOf<List<PrdOrder>>(emptyList()) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val responseObject = myOrdersViewModel.getPrdOrders(memberid.toInt(), PrdOrderStatus.Paid.status)
            prdorderlist = if(responseObject.respcode==0){
                responseObject.resobj as List<PrdOrder>
            }else{
                emptyList()
            }
            Log.d(tag, "prdorderlist=${prdorderlist.size}")
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .background(color = TipColor.deep_brown),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = TipColor.light_brown,
                    shape = RoundedCornerShape(size = 8.dp)
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
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
//                        color = Color(0xFF000000),
                        color = TipColor.deep_brown,
                        textAlign = TextAlign.Center,
                    ),
                    modifier = Modifier
                        .padding(5.dp)
                )
                Text(
                    text = ">待出貨訂單",
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
                                navController.navigate(route = "${MyOrdersScreens.MOS0401.name}/${prdorder.prdorderid}")
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
                                style = TextStyle(
                                    fontWeight = FontWeight(700),
                                    fontSize = 18.sp,
                                    color = TipColor.deep_brown,
                                ),
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = prdorder.createdate,
                                modifier = Modifier.weight(1f),
                                color = TipColor.deep_brown,
                                textAlign = TextAlign.End
                            )
                        }
                        Row {
                            Text(
                                text = "訂單金額: NTD ${prdorder.amount}",
                                color = TipColor.deep_brown,
                            )
                        }
                    }
                }
            }
        }
    }
}