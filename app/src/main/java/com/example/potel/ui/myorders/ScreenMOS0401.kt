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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
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
fun ScreenMOS0401(
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
                    text = ">待出貨訂單明細",
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

            Text(text = "訂單編號: AAA1234",
                fontFamily = FontFamily.SansSerif,
                style = TextStyle(fontWeight = FontWeight(700),
                    fontSize = 18.sp)
            )
            Text(text = "訂單金額: NTD 560")
            Text(text = "交易日期: 2024/12/15")

            Spacer(modifier = Modifier.height(12.dp))

            Text(text = "訂單內容:",
                fontFamily = FontFamily.SansSerif,
                style = TextStyle(fontWeight = FontWeight(700),
                    fontSize = 18.sp)
            )

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
                Text(text = "品名: 水盆")
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                )
                {
                    Text(
                        text = "金額: NTD 250",
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Start
                    )
                    Text(
                        text = "數量: 1",
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.End
                    )
                }

            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        border = BorderStroke(width = 1.dp, Color.Black),
                        shape = RoundedCornerShape(5)
                    )
                    .padding(5.dp),
            ) {
                Text(text = "品名: 飼料")
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                )
                {
                    Text(
                        text = "金額: NTD 155",
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Start
                    )
                    Text(
                        text = "數量: 2",
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.End
                    )
                }

            }

            OutlinedButton(
                onClick = {

                },
                shape = RoundedCornerShape(20),
                border = BorderStroke(width = 1.dp, color = Color.Black),
                contentPadding = PaddingValues(5.dp),
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(
                    text = "取消訂單",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.dm_sans)),
                        fontWeight = FontWeight(700),
                        color = Color(0xFF000000),
                    ),
                )
            }
        }
    }
}