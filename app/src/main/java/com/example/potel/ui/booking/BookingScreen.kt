package com.example.potel.ui.booking

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.potel.R

@Composable
fun BookingScreen(
    bookingVM: BookingViewModel,
    navController: NavHostController
) {
    var selectedDogWeight by remember { mutableStateOf<String?>(null) }
    var selectedCatRoom by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "請選擇狗的重量或貓的房型",
            style = MaterialTheme.typography.titleMedium,
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(15.dp))

        // 狗重量選擇 Row
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(70.dp),
//            horizontalArrangement = Arrangement.spacedBy(70.dp),
            modifier = Modifier
                .height(45.dp)
//                .background(color = Color(0xFFDBC8B6), shape = RoundedCornerShape(size = 8.dp))
                .padding(horizontal = 0.dp)
        ) {
            // 修改處：添加狗的重量前後的圖示
            Icon(
                painter = painterResource(id = R.drawable.searchicon),
                contentDescription = "Search Icon",
                modifier = Modifier.size(24.dp)
            )
            Text("狗的重量", fontSize = 18.sp)
            Icon(
                painter = painterResource(id = R.drawable.ilter),
                contentDescription = "Filter Icon",
                modifier = Modifier.size(48.dp)
            )
        }

        Spacer(modifier = Modifier.height(5.dp))
        val dogWeights = listOf("9公斤以下", "9-23公斤", "23-40公斤", "40公斤以上")
        dogWeights.forEach { weight ->
            val backgroundColor = if (selectedDogWeight == weight) Color(0xFFDBC8B6) else Color.Transparent
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 0.dp)
                    .background(color = backgroundColor, shape = RoundedCornerShape(8.dp))
                    .clickable {
                        selectedDogWeight = weight
                        selectedCatRoom = null // 清除貓房型選擇
                    }
            ) {
                RadioButton(
                    selected = selectedDogWeight == weight,
                    onClick = {
                        selectedDogWeight = weight
                        selectedCatRoom = null // 清除貓房型選擇
                    },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Color(0xFFAA8066), // 選中顏色
                        unselectedColor = Color.Gray // 未選中顏色（可選）
                    )
                )
                Text(text = weight, fontSize = 14.sp)
            }
        }

        Spacer(modifier = Modifier.height(15.dp))
        Divider(color = Color.Gray, thickness = 1.dp)
        Spacer(modifier = Modifier.height(15.dp))


        // 貓房型選擇 Row
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(70.dp),

            modifier = Modifier
                .height(45.dp)
//                .background(color = Color(0xFFDBC8B6), shape = RoundedCornerShape(size = 8.dp))
                .padding(horizontal = 0.dp)
        ) {
            // 修改處：添加貓的房型前後的圖示
            Icon(
                painter = painterResource(id = R.drawable.searchicon),
                contentDescription = "Search Icon",
                modifier = Modifier.size(24.dp)
            )
            Text("貓的房型", fontSize = 18.sp)
            Icon(
                painter = painterResource(id = R.drawable.ilter),
                contentDescription = "Filter Icon",
                modifier = Modifier.size(48.dp))
        }

        Spacer(modifier = Modifier.height(5.dp))
        val catRooms = listOf("標準房", "海景房", "鄉村房", "都市房")
        catRooms.forEach { room ->
            val backgroundColor = if (selectedCatRoom == room) Color(0xFFDBC8B6) else Color.Transparent

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 0.dp)
                    .background(color = backgroundColor, shape = RoundedCornerShape(8.dp))
                    .clickable {
                        selectedCatRoom = room
                        selectedDogWeight = null // 清除狗重量選擇
                    }
            ) {
                RadioButton(
                    selected = selectedCatRoom == room,
                    onClick = {
                        selectedCatRoom = room
                        selectedDogWeight = null // 清除狗重量選擇
                    },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Color(0xFFAA8066), // 選中顏色
                        unselectedColor = Color.Gray // 未選中顏色（可選）
                    )
                )
                Text(text = room)
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        Button(
            onClick = {
                val type = if (selectedDogWeight != null) 'D' else 'C'
                navController.navigate("RoomSelection/$type") // 導航到 RoomSelectionScreen，並傳遞類型
            },
            enabled = selectedDogWeight != null || selectedCatRoom != null,
            modifier = Modifier
                .height(45.dp)
                .width(400.dp)
                .background(Color.Transparent)
                .padding(0.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFAA8066)),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Booking Now", fontSize = 23.sp,fontWeight = FontWeight.Bold)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun pre2(){
    BookingScreen(bookingVM = viewModel(), rememberNavController())
}

