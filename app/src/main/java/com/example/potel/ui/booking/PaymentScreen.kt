package com.example.potel.ui.booking

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun PaymentScreen(navController: NavHostController) {
    var cardNumber by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("100") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("付款資訊", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = cardNumber, onValueChange = { cardNumber = it }, label = { Text("信用卡號碼") })
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = cvv, onValueChange = { cvv = it }, label = { Text("驗證碼") })
        Spacer(modifier = Modifier.height(16.dp))
        Text("金額: $$amount")
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { navController.navigate("success") }) {
            Text("提交")
        }
    }
}
