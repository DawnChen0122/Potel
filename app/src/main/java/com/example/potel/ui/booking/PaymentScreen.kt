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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.potel.R
import java.util.Date

@Composable
fun PaymentScreen(
    navController: NavHostController,
    bookingVM: BookingViewModel
) {
    val tag = "PaymentScreen"

//    val memberid = 22;
//    val cardNumber by bookingVM.creditCardNumber.collectAsState()
    val days by bookingVM.daySelectState.collectAsState()
    val selectedRoomType by bookingVM.roomTypeSelectedState.collectAsState()
    var cardNumber by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }

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
//                onValueChange = bookingVM::onCreditCardNumberChange,
                onValueChange = { cardNumber = it },
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
        order.amount = days * selectedRoomType.price
        Text("全部金額: $${order.amount}")

        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                // 創建一個新的 Order 對象並填充預設值
//                val newOrder = Order(
//                    memberid = 11, // 預設會員 ID，假設為 1
//                    roomtypeid = 101, // 預設房型 ID
//                    roomid = 22, // 預設房間 ID
//                    amount = 5000, // 預設金額
//                    expdates = Date().toString(), // 預設入住日期為當前日期
//                    expdatee = Date(System.currentTimeMillis() + (3 * 24 * 60 * 60 * 1000)).toString(), // 預設退房日期
//                    dates = Date().toString(), // 預設開始日期
//                    datee = Date(System.currentTimeMillis() + (3 * 24 * 60 * 60 * 1000)).toString(), // 預設結束日期
////                    refundamount = 0, // 預設退款金額
////                    paydatetime = null.toString(), // 預設未付款
////                    refunddatetime = null.toString(), // 預設未退款
////                    createdate = Date().toString() ,// 創建時間
//                    petid = 11,
////                    cardNumber = cardNumber,
////                    expiryDate = expiryDate,
////                    cvv = cvv
//
//                )



                Log.d(tag, "order.expdates=${order.expdates}")

                // 使用 ViewModel 將訂單添加到後端
                 bookingVM.addOrder(order)

                // 測試成功時的導航邏輯
                navController.navigate(BookingScreens.BookingSuccess.name)

            },
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text("提交") // 按鈕顯示文本
        }
    }
}

