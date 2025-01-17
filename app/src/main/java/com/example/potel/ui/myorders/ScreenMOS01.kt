package com.example.potel.ui.myorders

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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

@Composable
fun ScreenMOS01(
    navController: NavHostController
) {
    val tag = "ScreenMOS01"
    val myOrdersVM: MyOrdersViewModel = viewModel(key = "myOrdersVM")

    val context = LocalContext.current
    val preferences = context.getSharedPreferences("member", Context.MODE_PRIVATE)
    val memberid by remember { mutableStateOf(preferences.getString("memberid", "1")) }


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
                .fillMaxWidth()
                .weight(0.45f)
//                .background(color = Color(0xFFD9D9D9),
                .background(color = TipColor.light_brown,
            shape = RoundedCornerShape(size = 8.dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "訂房訂單",
                style = TextStyle(
                    fontSize = 24.sp,
                    lineHeight = 32.sp,
                    fontFamily = FontFamily(Font(R.font.dm_sans)),
                    fontWeight = FontWeight(700),
//                    color = Color(0xFF000000),
                    color = TipColor.deep_brown,
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(5.dp)
            )
            Button(
                shape = RoundedCornerShape(20),
                onClick = {
                    navController.navigate(route = "${MyOrdersScreens.MOS02.name}/$memberid")
                },
                border = BorderStroke(1.dp, Color.Black),
                colors = ButtonDefaults.outlinedButtonColors(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp)
            ) {
                Text(
                    text = "預約訂單",
                    style = TextStyle(
                        fontSize = 32.sp,
                        lineHeight = 32.sp,
                        fontFamily = FontFamily(Font(R.font.dm_sans)),
                        fontWeight = FontWeight(700),
//                        color = Color(0xFF000000),
                        color = TipColor.deep_brown,
                        textAlign = TextAlign.Center,
                    )
                )
            }

            Button(
                shape = RoundedCornerShape(20),
                onClick = {
                    navController.navigate(route = "${MyOrdersScreens.MOS03.name}/$memberid")
                },
                border = BorderStroke(1.dp, Color.Black),
                colors = ButtonDefaults.outlinedButtonColors(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp)
            ) {
                Text(
                    text = "歷史訂單",
                    style = TextStyle(
                        fontSize = 32.sp,
                        lineHeight = 32.sp,
                        fontFamily = FontFamily(Font(R.font.dm_sans)),
                        fontWeight = FontWeight(700),
//                        color = Color(0xFF000000),
                        color = TipColor.deep_brown,
                        textAlign = TextAlign.Center,
                    )
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.45f)
//              .background(color = Color(0xFFD9D9D9),
                .background(color = TipColor.light_brown,
            shape = RoundedCornerShape(size = 8.dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "購物訂單",
                style = TextStyle(
                    fontSize = 24.sp,
                    lineHeight = 32.sp,
                    fontFamily = FontFamily(Font(R.font.dm_sans)),
                    fontWeight = FontWeight(700),
//                    color = Color(0xFF000000),
                    color = TipColor.deep_brown,
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(5.dp)
            )

            Button(
                shape = RoundedCornerShape(20),
                onClick = {
                    navController.navigate(route = "${MyOrdersScreens.MOS04.name}/$memberid")
                },
                border = BorderStroke(1.dp, Color.Black),
                colors = ButtonDefaults.outlinedButtonColors(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp)
            ) {
                Text(
                    text = "待出貨訂單",
                    style = TextStyle(
                        fontSize = 32.sp,
                        lineHeight = 32.sp,
                        fontFamily = FontFamily(Font(R.font.dm_sans)),
                        fontWeight = FontWeight(700),
//                        color = Color(0xFF000000),
                        color = TipColor.deep_brown,
                        textAlign = TextAlign.Center,
                    )
                )
            }
            Button(
                shape = RoundedCornerShape(20),
                onClick = {
                    navController.navigate(route = "${MyOrdersScreens.MOS05.name}/$memberid")
                },
                border = BorderStroke(1.dp, Color.Black),
                colors = ButtonDefaults.outlinedButtonColors(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp)
            ) {
                Text(
                    text = "歷史訂單",
                    style = TextStyle(
                        fontSize = 32.sp,
                        lineHeight = 32.sp,
                        fontFamily = FontFamily(Font(R.font.dm_sans)),
                        fontWeight = FontWeight(700),
//                        color = Color(0xFF000000),
                        color = TipColor.deep_brown,
                        textAlign = TextAlign.Center,
                    )
                )
            }
        }
    }
}