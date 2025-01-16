package com.example.potel.ui.booking

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.potel.PotelApp
import com.example.potel.R
import com.example.potel.ui.theme.PotelTheme
import java.net.URL

@Composable
fun RoomSelectionScreen(
    bookingVM: BookingViewModel,
    navController: NavHostController,
    type: Char
) {
    val order = bookingVM.addOrderEditState.collectAsState().value
    val days by bookingVM.daySelectState.collectAsState()
    val selectedRoomType by bookingVM.roomTypeSelectedState.collectAsState()

    order.amount = days * order.price

    // 從 ViewModel 獲取房型列表
    val roomTypeList by bookingVM.roomTypesState.collectAsState()

    if (roomTypeList.isNullOrEmpty()) {
        Log.e("RoomSelectionScreen", "API 返回資料為空")
        Text("無法取得房型資料，請稍後再試。")
        return
    }

    // 根據 type 過濾房型列表
    val filteredRoomTypes = roomTypeList.filter {
        it.pettype == type // 確保 type 和 petType 一致為 Char
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "請選擇入住房型",fontSize = 18.sp,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp)
        )

        if (filteredRoomTypes.isEmpty()) {
            Text(
                text = "無可用房型。",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(filteredRoomTypes) { room ->
                    RoomCard(
                        roomType = room,
                        onSelectClick = {
                            bookingVM.setSelectedRoomType(roomType = room)
                            navController.navigate(BookingScreens.Payment.name)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun RoomCard(
    roomType: RoomType, // RoomType 是房型的數據模型
    onSelectClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(4.dp, RoundedCornerShape(16.dp)) // 增加陰影效果
            .background(Color.White) // 設置背景顏色為白色
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(0.dp) // 不增加項目之間的間距
        ) {
            // 使用 AsyncImage 來顯示圖片，並填滿到按鈕的距離
            AsyncImage(
                model = composeImageUrl(roomType.imageid),
                contentDescription = "房間照片",
                alignment = Alignment.TopCenter,
                contentScale = ContentScale.Crop, // 確保圖片填滿
                placeholder = painterResource(R.drawable.placeholder),
                modifier = Modifier
                    .fillMaxWidth() // 填滿寬度
                    .height(200.dp) // 設置固定高度以適應按鈕距離
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)) // 只設置上圓角
            )

            Button(
                onClick = onSelectClick,
                modifier = Modifier
                    .height(45.dp) // 高度設定
                    .fillMaxWidth() // 按鈕填滿寬度
                    .padding(0.dp), // 確保內部留白為 0
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFAA8066)), // 按鈕背景色
                shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp) // 設置下圓角
            ) {
                Text(text = "${roomType.descpt} - $${roomType.price}元", color = Color.White) // 按鈕文字顏色設為白色
            }
        }
    }
}




//@Preview(showBackground = true)
//@Composable
//fun pre3(){
//    RoomSelectionScreen(bookingVM = viewModel(), rememberNavController(), type = 'D')
//}


