package com.example.potel.ui.booking

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.potel.ui.theme.PotelTheme

@Composable
fun DateSelectionScreen(navController: NavHostController) {
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "選擇入住和離開日期", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(16.dp))
        // 假設這裡有 Date Picker，代入選擇日期的功能
        OutlinedTextField(
            value = startDate,
            onValueChange = { startDate = it },
            label = { Text("入住日期") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = endDate,
            onValueChange = { endDate = it },
            label = { Text("離開日期") }
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = { navController.navigate("booking") },
            enabled = startDate.isNotEmpty() && endDate.isNotEmpty()
        ) {
            Text("提交")
        }
    }
}
