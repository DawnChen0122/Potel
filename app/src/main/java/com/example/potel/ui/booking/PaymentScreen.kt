package com.example.potel.ui.booking

import android.util.Log
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.potel.R
import java.util.Date

@Composable
fun PaymentScreen(
    bookingVM: BookingViewModel,
    navController: NavHostController
) {
    val tag = "PaymentScreen"

    var cardNumber by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") } // 錯誤訊息

    val days by bookingVM.daySelectState.collectAsState()
    val selectedRoomType by bookingVM.roomTypeSelectedState.collectAsState()
    val order = bookingVM.addOrderEditState.collectAsState().value

    order.cardNumber = cardNumber
    order.expiryDate = expiryDate
    order.cvv = cvv

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
                .size(300.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Text("請輸入付款資訊", style = MaterialTheme.typography.titleMedium, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = cardNumber,
            onValueChange = { cardNumber = it },
            label = { Text("信用卡號碼") },
            isError = !isValidCardNumber(cardNumber),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        if (!isValidCardNumber(cardNumber)) {
            Text("信用卡號碼格式不正確！", color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = expiryDate,
                onValueChange = { expiryDate = it },
                label = { Text("到期日") },
                isError = !isValidExpiryDate(expiryDate),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )

            OutlinedTextField(
                value = cvv,
                onValueChange = { cvv = it },
                label = { Text("驗證碼") },
                isError = !isValidCVV(cvv),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        if (!isValidExpiryDate(expiryDate)) {
            Text("到期日格式應為 MM/YY！", color = MaterialTheme.colorScheme.error)
        }
        if (!isValidCVV(cvv)) {
            Text("驗證碼應為 3 或 4 位數字！", color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(16.dp))
        order.amount = days * selectedRoomType.price
        Text("全部金額: $${order.amount}元", fontSize = 18.sp)

        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                if (isValidCardNumber(cardNumber) && isValidExpiryDate(expiryDate) && isValidCVV(cvv)) {
                    bookingVM.addOrder(order)
                    navController.navigate(BookingScreens.BookingSuccess.name)
                } else {
                    errorMessage = "請正確填寫所有資訊！"
                }
            },
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text("提交", fontSize = 18.sp)
        }
        if (errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(errorMessage, color = MaterialTheme.colorScheme.error)
        }
    }
}

// 檢查信用卡號碼是否有效
fun isValidCardNumber(cardNumber: String): Boolean {
    return Regex("^\\d{16}$").matches(cardNumber)
}

// 檢查到期日格式是否正確
fun isValidExpiryDate(expiryDate: String): Boolean {
    return Regex("^\\d{2}/\\d{2}$").matches(expiryDate)
}

// 檢查 CVV 是否有效
fun isValidCVV(cvv: String): Boolean {
    return Regex("^\\d{3,4}$").matches(cvv)
}


@Preview(showBackground = true)
@Composable
fun pre4(){
    PaymentScreen(bookingVM = viewModel(), rememberNavController())
}