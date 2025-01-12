package com.example.potel.ui.booking

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.potel.R

@Composable
fun PaymentScreen(
    navController: NavHostController,
    bookingVM: BookingViewModel
) {
    val memberId =13
    val cardNumber by bookingVM.creditCardNumber.collectAsState()
    val days by bookingVM.daySelectState.collectAsState()
    val selectedRoomType by bookingVM.roomTypeSelectedState.collectAsState()
    var expiryDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    val amount = "100"


    val fieldModifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.credit),
            contentDescription = "Room Image",
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Text("付款資訊", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(16.dp))


        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = cardNumber,
                onValueChange = bookingVM::onCreditCardNumberChange,
                label = { Text("信用卡號碼") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = fieldModifier.weight(2f) // 使用權重控制寬度
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = expiryDate,
                onValueChange = { updatedValue -> expiryDate = updatedValue },
                label = { Text("到期日") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )

            OutlinedTextField(
                value = cvv,
                onValueChange = { updatedValue -> cvv = updatedValue },
                label = { Text("驗證碼") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        val totalAmount = days * selectedRoomType.price
        Text("金額: $$totalAmount")

        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                val newOrder =newOrder(
                    memberId=memberId,
                    roomId = 99,
                    expdatee = "2025-01-09",
                    expdates = "2025-01-11",
                    amount = 1000,
                    roomTypeId = 99,
                    petId = 99
                )
                bookingVM.addOrder(newOrder)
                navController.navigate("BookingSuccess") },
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text("提交")
        }
    }
}

