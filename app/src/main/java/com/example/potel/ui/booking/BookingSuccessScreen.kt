package com.example.potel.ui.booking

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight // 引入 FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.potel.R

fun maskCardNumber(cardNumber: String): String {
    return if (cardNumber.length == 16) {
        val start = cardNumber.take(4)
        val end = cardNumber.takeLast(4)
        "$start-****-****-$end"
    } else {
        "****-****-****-****"
    }
}

@Composable
fun BookingSuccessScreen(
    bookingVM: BookingViewModel,
    navController: NavHostController
) {
    val order = bookingVM.addOrderEditState.collectAsState().value
    val selectedRoomType by bookingVM.roomTypeSelectedState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFFDBC8B6)), // 整體背景顏色
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ElevatedCard(
            modifier = Modifier
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp), // 設置圓角
            colors = CardDefaults.elevatedCardColors(containerColor = Color(0xFFF5F5F5)) // 設置背景顏色
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp) // 增加項目之間的間距
            ) {
                // 使用 Box 來重疊圖片
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center // 讓內容置中
                ) {
                    // Checker 圖片位於底部
                    Image(
                        painter = painterResource(id = R.drawable.checker),
                        contentDescription = "Checker Image",
                        modifier = Modifier
                            .size(200.dp) // 調整圖片大小以適應卡片
                            .clip(RoundedCornerShape(50)) // 圓形圖片
//                            .align(Alignment.Center),
,
                        contentScale = ContentScale.Crop
                    )
                    // Vector 圖片位於上面，重疊在 Checker 上面
                    Image(
                        painter = painterResource(id = R.drawable.vector),
                        contentDescription = "Vector Image",
                        modifier = Modifier
                            .size(60.dp) // 調整圖片大小以適應卡片
                            .clip(RoundedCornerShape(50)) // 圓形圖片
                            .offset(y = (-20).dp)
//                            .align(Alignment.Center) // 可選：調整位置，根據需要放置
                        ,
                        contentScale = ContentScale.Crop
                    )
                }

                // 文字靠左對齊，紅色字體加粗
                Text("預約成功！期待您的到來！",
                    style = MaterialTheme.typography.titleLarge.copy(color = Color(0xFFDA291C), fontWeight = FontWeight.Bold)) // 強調顏色並加粗

                Text("訂單編號：${order.orderid}", style = MaterialTheme.typography.bodyMedium, fontSize = 16.sp)
                Text("預約會員：${order.member.name}", style = MaterialTheme.typography.bodyMedium, fontSize = 16.sp)
                Text("入住寵物：${order.nickname}", style = MaterialTheme.typography.bodyMedium, fontSize = 16.sp)
                Text("入住房間：${selectedRoomType.descpt}", style = MaterialTheme.typography.bodyMedium, fontSize = 16.sp)
                Text("信用卡號：${maskCardNumber(order.cardNumber ?: "")}", style = MaterialTheme.typography.bodyMedium, fontSize = 16.sp)
                Text("入住時間：${order.expdates}", style = MaterialTheme.typography.bodyMedium, fontSize = 16.sp)
                Text("離開時間：${order.expdatee}", style = MaterialTheme.typography.bodyMedium, fontSize = 16.sp)
                Text("全部金額：$${order.amount}",
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFFDA291C), fontWeight = FontWeight.Bold, fontSize = 16.sp)) // 強調金額顏色並加粗

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun pre5() {
    BookingSuccessScreen(bookingVM = viewModel(), navController = rememberNavController())
}
