package com.example.potel.ui.booking

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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
    var isSubmitted by remember { mutableStateOf(false) } // 是否已提交
    var errorMessage by remember { mutableStateOf("") }

    val days by bookingVM.daySelectState.collectAsState()
    val selectedRoomType by bookingVM.roomTypeSelectedState.collectAsState()
    val order = bookingVM.addOrderEditState.collectAsState().value

    order.cardNumber = cardNumber
    order.expiryDate = expiryDate
    order.cvv = cvv

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.creditcard),
            contentDescription = "Room Image",
            modifier = Modifier
                .size(350.dp)
//                .clip(RoundedCornerShape(8.dp)),
//            contentScale = ContentScale.Crop
        )

        Text("請輸入付款資訊", style = MaterialTheme.typography.titleMedium, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(16.dp))

        // 信用卡號碼輸入
        OutlinedTextField(
            value = cardNumber,
            onValueChange = { cardNumber = it },
            label = { Text("信用卡號碼", color = Color.Black) },
            isError = isSubmitted && !isValidCardNumber(cardNumber),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        if (isSubmitted && !isValidCardNumber(cardNumber)) {
            Text("信用卡號碼格式不正確！", color = Color(0xFFDA291C))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 到期日和驗證碼輸入
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = expiryDate,
                onValueChange = { expiryDate = it },
                label = { Text("到期日", color = Color.Black) },
                isError = isSubmitted && !isValidExpiryDate(expiryDate),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )

            OutlinedTextField(
                value = cvv,
                onValueChange = { cvv = it },
                label = { Text("驗證碼", color = Color.Black) },
                isError = isSubmitted && !isValidCVV(cvv),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
        }

        if (isSubmitted && !isValidExpiryDate(expiryDate)) {
            Text("到期日格式應為 MM/YY！", color = Color(0xFFDA291C))
        }
        if (isSubmitted && !isValidCVV(cvv)) {
            Text("驗證碼應為 3 或 4 位數字！", color = Color(0xFFDA291C))
        }

        Spacer(modifier = Modifier.height(16.dp))

        order.amount = days * selectedRoomType.price
        Text("全部金額: $${order.amount}元", fontSize = 18.sp)

        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                isSubmitted = true
                if (isValidCardNumber(cardNumber) && isValidExpiryDate(expiryDate) && isValidCVV(cvv)) {
                    bookingVM.addOrder(order)
                    navController.navigate(BookingScreens.BookingSuccess.name)
                } else {
                    errorMessage = "請正確填寫所有資訊！"
                }
            },
//            modifier = Modifier.padding(horizontal = 0.dp)
//            modifier = Modifier.fillMaxWidth()
            modifier = Modifier
                .height(45.dp) // 高度設定
                .width(400.dp)
                .background(Color.Transparent) // 設置背景為透明以避免重疊顏色
                .padding(0.dp), // 確保內部留白為 0
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDA291C)), // 按鈕背景色
            shape = RoundedCornerShape(8.dp) // 設置圓角


        ) {
            Text("Confirm", fontSize = 23.sp,fontWeight = FontWeight.Bold)
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