package com.example.potel.ui.booking

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun RoomSelectScreen(navController: NavHostController, type: String) {
    val rooms = if (type == "dog") {
        listOf("小型狗房 - $100", "中型狗房 - $150", "大型狗房 - $200", "特大狗房 - $250")
    } else {
        listOf("標準貓房 - $80", "海景貓房 - $120", "鄉村貓房 - $100", "都市貓房 - $150")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("選擇房型", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(rooms) { room ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(room, Modifier.weight(1f))
                    Button(onClick = { navController.navigate("payment") }) {
                        Text("選擇")
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
