package com.example.potel.ui.shopping

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.potel.ui.theme.PotelTheme

@Composable
fun CreditCardScreen(viewModel: CreditCardViewModel = viewModel(), navController: NavHostController) {    // 使用 remember 和 mutableStateOf 來保存並更新顯示的文本

    val cardnumber  by viewModel.cardnumber.collectAsState()

    var expiredate by remember { mutableStateOf("") }

    var safecode by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(50.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        // 顯示標題
        Text(
            text = "信用卡資料",
            fontSize = 35.sp, // 字型大小: 35
            fontWeight = FontWeight.Bold, // 字體樣式: 粗體
            color = Color.Black, // 字的顏色: 黑色
            modifier = Modifier.padding(bottom = 30.dp) // 下方間距
        )

        OutlinedTextField(
            value = cardnumber,
            onValueChange = viewModel::onCardnumberChanged,
            label = { Text(text = "卡號") },
            singleLine = true,
            shape = RoundedCornerShape(20.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
        )

        if (viewModel.cardnumberError) {
            Text(
                text = "信用卡卡號為十六位數字",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp)
            )
        }


//        Text(text = "MM/YY")
        OutlinedTextField(
            value = expiredate,
            onValueChange = { expiredate = it },
            label = { Text(text = "MM/YY") },
            singleLine = true,
            shape = RoundedCornerShape(20.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            modifier = Modifier
                .fillMaxWidth()
//                .background(Color.DarkGray, shape = RoundedCornerShape(12.dp))
                .padding(top = 8.dp),
            textStyle = TextStyle(
                fontSize = 16.sp,
                color = Color.Black
            ),
            colors = OutlinedTextFieldDefaults.colors(
            )
        )

        OutlinedTextField(
            value = safecode,
            onValueChange = { safecode = it },
            label = { Text(text = "CVV") },
            singleLine = true,
            shape = RoundedCornerShape(20.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
        )

        // 顯示按鈕
        Button(
            onClick = {navController.navigate(ShopScreens.Ordercheck.name)},
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black, // 設定按鈕容器顏色
                contentColor = Color.Yellow // 設定按鈕內容顏色
            ),
            modifier = Modifier.padding(top = 100.dp) // 上方間距
        ) {
            Text(
                text = "輸入完成", // 按鈕文字
                fontSize = 20.sp
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun CreditCardPreview() {
//    PotelTheme{
//        CreditCardScreen()
//    }
//}