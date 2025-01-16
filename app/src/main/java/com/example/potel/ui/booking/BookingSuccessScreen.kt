package com.example.potel.ui.booking

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.potel.R

/**
 * 將信用卡號格式化，顯示前四位和後四位，中間用 * 遮罩
 */
fun maskCardNumber(cardNumber: String): String {
    return if (cardNumber.length == 16) {
        val start = cardNumber.take(4)
        val end = cardNumber.takeLast(4)
        "$start-****-****-$end"
    } else {
        "****-****-****-****" // 如果卡號長度不是16，顯示預設遮罩
    }
}

@Composable
fun BookingSuccessScreen(
    bookingVM: BookingViewModel,
    navController: NavHostController
) {
    val tag = "BookingSuccessScreen"

    val order = bookingVM.addOrderEditState.collectAsState().value
    val days by bookingVM.daySelectState.collectAsState()
    val selectedRoomType by bookingVM.roomTypeSelectedState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.checkmark),
            contentDescription = "image",
            modifier = Modifier
                .size(150.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("    預約成功！\n期待您的到來！", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Text("訂單編號：${order.orderid}")
        Spacer(modifier = Modifier.height(16.dp))
        Text("會員: ${order.member.name}")
        Spacer(modifier = Modifier.height(16.dp))
        Text("寵物: ${order.nickname}")
        Spacer(modifier = Modifier.height(16.dp))
        Text("房間: ${selectedRoomType.descpt}")
        Spacer(modifier = Modifier.height(16.dp))
        Text("信用卡號: ${maskCardNumber(order.cardNumber ?: "")}")
        Spacer(modifier = Modifier.height(16.dp))
        Text("入住時間: ${order.expdates}")
        Spacer(modifier = Modifier.height(16.dp))
        Text("離開時間: ${order.expdatee}")
        Spacer(modifier = Modifier.height(16.dp))
        Text("全部金額: $${order.amount}")
    }
}

@Preview(showBackground = true)
@Composable
fun pre5(){
    BookingSuccessScreen(bookingVM = viewModel(), rememberNavController())
}