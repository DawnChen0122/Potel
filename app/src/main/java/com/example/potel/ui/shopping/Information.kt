package com.example.potel.ui.shopping

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.potel.ui.theme.TipColor


@Composable
fun InformationScreen(
    navController: NavHostController,
    shopViewModel: ShopViewModel,
    prdId: String
) {
    val tag = "InformationScreen"
    val count by shopViewModel.productCount.collectAsState()
    val product by shopViewModel.product.collectAsState()
    var amount = count * (product?.price ?: 0)


    LaunchedEffect(Unit) {
        Log.d(tag, "prdId=$prdId")
        shopViewModel.getProduct(prdId.toInt())
    }

//    var title by viewModel.title.collectAsState()
//    LaunchedEffect(count) { amount = count * 230 }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(50.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        // 顯示圖片，並設置圓角
//        Image(
//            painter = painterResource(id = R.drawable.salmonfro),
//            contentDescription = "商品圖片",
//            modifier = Modifier
//                .size(250.dp) // 圖片大小設為 250.dp
//                .clip(RoundedCornerShape(16.dp)), // 圓角邊框
//            contentScale = ContentScale.Fit // 剪裁圖片來適應容器
//        )

        AsyncImage(
            model = composeImageUrl(product?.imageId ?: 0),
            contentDescription = "寵物照片",
            alignment = Alignment.Center,
            modifier = Modifier
                .size(250.dp) // 圖片大小設為 250.dp
                .clip(RoundedCornerShape(16.dp)), // 圓角邊框
            contentScale = ContentScale.FillHeight,
        )


        // 顯示標題
        Text(
            text = product?.prdName ?: "",
            fontSize = 24.sp, // 字型大小: 24sp
            fontWeight = FontWeight.Bold, // 字體樣式: 粗體
            color = Color.Black, // 字的顏色: 黑色
            modifier = Modifier.padding(top = 0.dp) // 上方間隔
        )

        //顯示內文
        Text(
            text = product?.prdDesc ?: "",
            fontSize = 16.sp, // 字型大小: 16sp
            fontWeight = FontWeight.Thin, // 字體樣式: 細體
            color = Color.DarkGray, // 字的顏色: 深灰色
            modifier = Modifier.padding(top = 0.dp) // 上方間隔
        )


        Row(
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
        ) {

            Box(
                modifier = Modifier
                    .padding(8.dp)
//                    .background(Color.Yellow, shape = CircleShape)
                    .background(TipColor.light_brown, shape = CircleShape)
            ) {
                Image(
                    modifier = Modifier
                        .size(36.dp)
                        .clickable {
                            val newCount = count - 1
                            shopViewModel.onCountChanged(newCount)
                        }, imageVector = Icons.Default.ArrowDropDown, contentDescription = null
                )
            }

            Text(
                fontSize = 28.sp,
                text = count.toString()
            )

            Box(
                modifier = Modifier
                    .padding(8.dp)
//                    .background(Color.Yellow, shape = CircleShape)
                    .background(TipColor.light_brown, shape = CircleShape)
            ) {

                Image(
                    modifier = Modifier
                        .size(36.dp)
                        .clickable {
                            val newCount = count + 1
                            shopViewModel.onCountChanged(newCount)
                        },
                    imageVector = Icons.Default.KeyboardArrowUp, contentDescription = null
                )
            }
        }


        Row(
            modifier = Modifier
                .padding(top = 40.dp)
                .border(width = 2.dp, color = Color.Gray, shape = RoundedCornerShape(30))
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {

            Text(
                text = "總價:",
                fontSize = 24.sp, // 字型大小: 24
                fontWeight = FontWeight.Bold, // 字體樣式: 粗體
                color = Color.Black, // 字的顏色: 黑色
                modifier = Modifier.padding(bottom = 0.dp) // 下方間距
            )
            Text(
                text = amount.toString(),
                fontSize = 28.sp, // 字型大小: 28
                fontWeight = FontWeight.Bold, // 字體樣式: 粗體
                color = TipColor.bright_red, // 字的顏色: 紅色
                modifier = Modifier.padding(bottom = 0.dp) // 下方間距
            )
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(115.dp)
                .background(Color.Transparent)
                .padding(horizontal = 10.dp), // 內部水平間隔
            horizontalArrangement = Arrangement.Center, // 子元素水平置中
            verticalAlignment = Alignment.CenterVertically // 子元素垂直置中
        ) {
            Button(
                onClick = { navController.navigate(ShopScreens.Creditcard.name) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = TipColor.light_brown, // 按鈕背景色
                    contentColor = TipColor.deep_brown // 按鈕文字顏色
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

//@Preview(showBackground = true)
//    @Composable
//    fun InformationPreview() {
//        PotelTheme {
//            InformationScreen()
//        }
//    }