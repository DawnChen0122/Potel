package com.example.potel.ui.booking

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.potel.R

@Composable
fun BookingScreen(
    bookingVM: BookingViewModel,
    navController: NavHostController
) {
    var selectedDogWeight by remember { mutableStateOf<String?>(null) }
    var selectedCatRoom by remember { mutableStateOf<String?>(null) }
    var selectedPetType by remember { mutableStateOf<Char?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "請選擇狗的重量或貓的房型", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(16.dp))

        // 狗重量選擇
        Text("狗的重量")
        val dogWeights = listOf("9公斤以下", "9-23公斤", "23-40公斤", "40公斤以上")
        dogWeights.forEach { weight ->
            Row {
                RadioButton(
                    selected = selectedDogWeight == weight,
                    onClick = {
                        selectedDogWeight = weight
                        selectedCatRoom = null // 清除貓房型選擇
                    }
                )
                Text(text = weight)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 貓房型選擇
        Text("貓的房型")
        val catRooms = listOf("標準房", "海景房", "鄉村房", "都市房")
        catRooms.forEach { room ->
            Row {
                RadioButton(
                    selected = selectedCatRoom == room,
                    onClick = {
                        selectedCatRoom = room
                        selectedDogWeight = null // 清除狗重量選擇
                    }
                )
                Text(text = room)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val type = if (selectedDogWeight != null) 'D' else 'C'
                navController.navigate("RoomSelection/$type") // 導航到 RoomSelectionScreen，並傳遞類型
//                Log.d("Navigation","P2 to P3")
            },
            enabled = selectedDogWeight != null || selectedCatRoom != null // 按鈕啟用條件
        ) {
            Text("Booking Now")
        }
    }
}


