package com.example.potel.ui.shopping

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.potel.R
import com.example.potel.ui.theme.PotelTheme

@Composable
fun InformationScreen() {

    var count by remember { mutableStateOf(1) }
    var amount by remember { mutableStateOf(1) }
    LaunchedEffect(count) { amount = count * 230 }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        // 顯示圖片，並設置圓角
        Image(
            painter = painterResource(id = R.drawable.salmonfro),
            contentDescription = "商品圖片",
            modifier = Modifier
                .size(250.dp) // 圖片大小設為 250.dp
                .clip(RoundedCornerShape(16.dp)), // 圓角邊框
            contentScale = ContentScale.Fit // 剪裁圖片來適應容器
        )


        // 顯示標題
        Text(
            text = "【優格】鮮肉佐餐凍乾(鮭魚配方)\n60克(狗零食)",
            fontSize = 24.sp, // 字型大小: 24sp
            fontWeight = FontWeight.Bold, // 字體樣式: 粗體
            color = Color.Black, // 字的顏色: 黑色
            modifier = Modifier.padding(top = 0.dp) // 上方間隔
        )
        //顯示內文
        Text(
            text = "【成份：雞鮮肉、樹薯粉、鮭魚鮮肉、糙米粉、啤酒酵母、紅藜麥、鮭魚油、蕃茄粉、南瓜粉、益生菌（腸球F菌、嗜酸乳桿菌、乳雙歧桿菌、鼠李糖乳桿菌）、維生素E",
            fontSize = 16.sp, // 字型大小: 16sp
            fontWeight = FontWeight.Thin, // 字體樣式: 細體
            color = Color.DarkGray, // 字的顏色: 深灰色
            modifier = Modifier.padding(top = 0.dp) // 上方間隔
        )


        Row(
            modifier = Modifier.padding(top = 40.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
        ) {

            Box(modifier = Modifier.padding(8.dp).background(Color.Yellow, shape = CircleShape)) {
                Image(
                    modifier = Modifier.size(36.dp).clickable {
                        if (count > 0) {
                            count--
                        }
                    }, imageVector = Icons.Default.ArrowDropDown, contentDescription = null
                )
            }

            Text(
                fontSize = 28.sp,
                text = count.toString()
            )

            Box(modifier = Modifier.padding(8.dp).background(Color.Yellow, shape = CircleShape)) {

                Image(
                    modifier = Modifier.size(36.dp).clickable { count++ },
                    imageVector = Icons.Default.KeyboardArrowUp, contentDescription = null
                )
            }
        }


        Row(
            modifier = Modifier.padding(top = 40.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ){

            Text(
                text = "總價:",
                fontSize = 24.sp, // 字型大小: 24
                fontWeight = FontWeight.Bold, // 字體樣式: 粗體
                color = Color.Red, // 字的顏色: 紅色
                modifier = Modifier.padding(bottom = 0.dp) // 下方間距
            )
            Text(
                text = amount.toString(),
                fontSize = 28.sp, // 字型大小: 28
                fontWeight = FontWeight.Bold, // 字體樣式: 粗體
                color = Color.Red, // 字的顏色: 紅色
                modifier = Modifier.padding(bottom = 0.dp) // 下方間距
            )
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.Transparent)
                .padding(horizontal = 10.dp), // 內部水平間隔
            horizontalArrangement = Arrangement.Center, // 子元素水平置中
            verticalAlignment = Alignment.CenterVertically // 子元素垂直置中
        ) {
            Button(
                onClick = { /* 處理按鈕點擊事件 */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black, // 按鈕背景色
                    contentColor = Color.Yellow // 按鈕文字顏色
                )
            ) {
                Text(
                    text = "確認購買", // 按鈕上的文字
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
    @Composable
    fun InformationPreview() {
        PotelTheme {
            InformationScreen()
        }
    }