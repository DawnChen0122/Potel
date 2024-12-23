package com.example.potel.ui.myorders

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavHostController
import com.example.potel.R

@Composable
fun ScreenMOS0303(
//    myOrdersViewModel: MyOrdersViewModel = viewModel(),
    navController: NavHostController
) {
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
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
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
                            color = Color(0xFF000000),
                            textAlign = TextAlign.Center,
                        ),
                        modifier = Modifier
                            .padding(5.dp)
                    )
                    Text(
                        text = ">我要評分",
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

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "感謝您的評分與評價",
                    style = TextStyle(
                        fontSize = 30.sp,
                        lineHeight = 32.sp,
                        fontFamily = FontFamily(Font(R.font.dm_sans)),
                        fontWeight = FontWeight(1000),
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Start
                    ),
                    modifier = Modifier
                        .padding(5.dp)
                )
                Text(
                    text = "我們會更努力改善我們的服務!",
                    style = TextStyle(
                        fontSize = 20.sp,
                        lineHeight = 32.sp,
                        fontFamily = FontFamily(Font(R.font.dm_sans)),
                        fontWeight = FontWeight(1000),
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Start
                    ),
                    modifier = Modifier
                        .padding(5.dp)
                )
            }
        }
    }
}
