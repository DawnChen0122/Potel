package com.example.potel.ui.booking

import android.util.Log
import androidx.compose.foundation.background
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
    val tag = "BookingScreen"

    var selectedDogWeight by remember { mutableStateOf<String?>(null) }
    var selectedCatRoom by remember { mutableStateOf<String?>(null) }
    var selectedPetType by remember { mutableStateOf<Char?>(null) }

    val order = bookingVM.addOrderEditState.collectAsState().value




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

        Spacer(modifier = Modifier.height(20.dp))


        Row(// 內部子元件的垂直對齊方式，預設為 Alignment.Top
            verticalAlignment = Alignment.CenterVertically,
            // 內部子元件之間的水平間隔空間大小
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .height(45.dp)
                .background(color = Color(0xFFDBC8B6),shape = RoundedCornerShape(size = 8.dp))
                .padding(horizontal = 18.dp)) {
            // 狗重量選擇
            Text("狗的重量",fontSize = 18.sp)
        }

//        Text("狗的重量",fontSize = 18.sp)
        Spacer(modifier = Modifier.height(5.dp))
        val dogWeights = listOf("9公斤以下", "9-23公斤", "23-40公斤", "40公斤以上")
        dogWeights.forEach { weight ->
            Row (verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(84.dp,0.dp)

            ){

                RadioButton(
                    selected = selectedDogWeight == weight,
                    onClick = {
                        selectedDogWeight = weight
                        selectedCatRoom = null // 清除貓房型選擇
                    }
                )
                Text(text = weight,fontSize = 14.sp)
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Row(
            // 內部子元件的垂直對齊方式，預設為 Alignment.Top
            verticalAlignment = Alignment.CenterVertically,

            // 內部子元件之間的水平間隔空間大小
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .height(45.dp)
                .background(color = Color(0xFFDBC8B6),shape = RoundedCornerShape(size = 8.dp))
                .padding(horizontal = 18.dp)) {
            // 貓房型選擇

            Text("貓的房型",fontSize = 18.sp)



        }


//        Text("貓的房型",fontSize = 18.sp)
        Spacer(modifier = Modifier.height(5.dp))
        val catRooms = listOf("標準房", "海景房", "鄉村房", "都市房")
        catRooms.forEach { room ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(84.dp,0.dp)
            ) {
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

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                val type = if (selectedDogWeight != null) 'D' else 'C'
                navController.navigate("RoomSelection/$type") // 導航到 RoomSelectionScreen，並傳遞類型
//                Log.d("Navigation","P2 to P3")
            },
            enabled = selectedDogWeight != null || selectedCatRoom != null, // 按鈕啟用條件

        ) {
            Text("預訂",fontSize = 18.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun pre(){
    BookingScreen(bookingVM = viewModel(), rememberNavController())
}

