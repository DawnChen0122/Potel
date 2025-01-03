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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.potel.PotelApp
import com.example.potel.R
import com.example.potel.ui.theme.PotelTheme

//@Composable
//fun RoomSelectionScreen(navController: NavHostController, type: String) {
//    val rooms = if (type == "dog") {
//        listOf("小型狗房 - $100", "中型狗房 - $150", "大型狗房 - $200", "特大狗房 - $250")
//    } else {
//        listOf("標準貓房 - $80", "海景貓房 - $120", "鄉村貓房 - $100", "都市貓房 - $150")
//    }
//    Log.d("RoomSelectionScreen", "here1")
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        Text(
//            "請選擇入住房型",
//            style = MaterialTheme.typography.titleMedium,
//            modifier = Modifier.align(Alignment.CenterHorizontally)
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        rooms.forEach { room ->
//            Row(
//                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Text(text = room, style = MaterialTheme.typography.bodyMedium)
//
//                Button(onClick = {
//                    navController.navigate("Payment") // 導航到付款頁面
//                }) {
//                    Text("選擇")
//                }
//            }
//        }
//    }
//}


@Composable
fun RoomSelectionScreen(navController: NavHostController, type: String) {
    val rooms = if (type == "dog") {
        listOf("小型狗房 - $100", "中型狗房 - $150", "大型狗房 - $200", "特大狗房 - $250")
    } else {
        listOf("標準貓房 - $80", "海景貓房 - $120", "鄉村貓房 - $100", "都市貓房 - $150")
    }

    // 加入 ScrollState
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState) // 垂直滾動
    ) {
        Text(
            "選擇房型",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally) // 水平置中
        )
        Spacer(modifier = Modifier.height(16.dp))

        rooms.forEach { room ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center // 水平與垂直置中
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = room,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.align(Alignment.CenterVertically) // 垂直置中
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Image(
                        painter = painterResource(id = R.drawable.roomtest),
                        contentDescription = "image",
                        modifier = Modifier
                            .size(250.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Button(
                onClick = { navController.navigate("payment") },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally) // 按鈕水平置中
            ) {
                Text("選擇")
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

