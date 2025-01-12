package com.example.potel.ui.myorders

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
fun ScreenMOS04(
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
                        color = Color(0xFF000000),
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
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Start
                    ),
                    modifier = Modifier
                        .padding(5.dp)
                )
            }

            // 一筆資料
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        border = BorderStroke(width = 1.dp, Color.Black),
                        shape = RoundedCornerShape(5)
                    )
                    .padding(5.dp),
//                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                )
                {
                    Text(text = "訂單編號: AAA1234",
                        fontFamily = FontFamily.SansSerif,
                        style = TextStyle(fontWeight = FontWeight(700),
                            fontSize = 18.sp),
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "2024/12/15",
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.End
                    )
                }
                Row { Text(text = "訂單金額: NTD 560") }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        border = BorderStroke(width = 1.dp, Color.Black),
                        shape = RoundedCornerShape(5)
                    )
                    .padding(5.dp),
//                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                )
                {
                    Text(text = "訂單編號: AAA1234",
                        fontFamily = FontFamily.SansSerif,
                        style = TextStyle(fontWeight = FontWeight(700),
                            fontSize = 18.sp),
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "2024/12/15",
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.End
                    )
                }
                Row { Text(text = "訂單金額: NTD 560") }
            }
        }
    }
}