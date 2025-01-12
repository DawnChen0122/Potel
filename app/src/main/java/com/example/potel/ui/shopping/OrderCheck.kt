package com.example.potel.ui.shopping

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController

@Composable
fun OrderCheckScreen(
    shopViewModel: ShopViewModel,
    navController: NavHostController,
    onDismissRequest: () -> Unit,
) {

    val completeOrderId by shopViewModel.completeOrderId.collectAsState()
    LaunchedEffect(completeOrderId) {
        Log.d("completeOrder", "completeOrderId: $completeOrderId")
    }

    Dialog(onDismissRequest = onDismissRequest) {
        // 使用 Card 來呈現對話框內容
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .shadow(elevation = 2.dp, shape = RoundedCornerShape(16))
                .border(width = 2.dp, color = Color.Yellow, shape = RoundedCornerShape(16))
                .background(Color.White)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp) // 設定圓角
        ) {
            // 用 Column 來垂直排列所有元素
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 顯示圖片，這裡使用了 painterResource 來加載圖片資源
//                Image(
//                    painter = painterResource(id = R.drawable.room), // 用您自己的圖片資源替換
//                    contentDescription = "Order Completed",
//                    contentScale = ContentScale.Fit,
//                    modifier = Modifier.aspectRatio(2.5f) // 調整圖片的長寬比例
//                )

                // 顯示文字提示
                Text(
                    text = "您的訂單已完成!",
                    modifier = Modifier.padding(16.dp),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold // 字體樣式: 粗體
                )
                Text(
                    text = "訂單編號:\n$completeOrderId",
                    modifier = Modifier.padding(16.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold // 字體樣式: 粗體
                )

                // 回到首頁按鈕，設置 onClick 邏輯
                Button(
                    onClick = {
                        navController.navigate(ShopScreens.Twoclass.name)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black, // 按鈕顏色
                        contentColor = Color.Yellow // 按鈕中文字顏色
                    )
                ) {
                    Text(
                        text = "回到首頁",
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun OrderCheckPreview() {
//    PotelTheme {
//        OrderCheckScreen()
//    }
//}