package com.example.potel.ui.booking

import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
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
    val tag = "RoomSelectionScreen"

    // 從 ViewModel 獲取房型列表
    val roomTypeList by bookingVM.roomTypesState.collectAsState()

    // 檢查是否資料為空
    if (roomTypeList.isNullOrEmpty()) {
        Log.e(tag, "API 返回資料為空")
        Text("無法取得房型資料，請稍後再試。")
        return
    }

    // 根據 type 過濾房型列表
    val filteredRoomTypes = roomTypeList.filter {
        Log.d(tag, "it.petType=${it.pettype}, type=$type")
        it.pettype == type // 確保 type 和 petType 一致為 Char
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "選擇房型",
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
    val tag = "RoomCard"

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "${roomType.descpt} - $${roomType.price}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.width(16.dp))

                //                val imageUrl = "http://10.0.2.2:8080/PotelServer/booking/image?imageId=${roomType.imageId}"
                val imageurl = composeImageUrl(roomType.imageid)
                Log.d(tag, "Loading image from URL: $imageurl")

                AsyncImage(
//                    model = "http://10.0.2.2:8080/PotelServer/api/image?imageId=28",
//                    model = com.example.potel.ui.booking.composeImageUrl(RoomType.imageId),
                    model = imageurl,
                    contentDescription = "房間照片",
                    alignment = Alignment.TopCenter,
                    contentScale = ContentScale.FillWidth,
                    placeholder = painterResource(R.drawable.placeholder)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onSelectClick,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("選擇")
            }
        }
    }
}


