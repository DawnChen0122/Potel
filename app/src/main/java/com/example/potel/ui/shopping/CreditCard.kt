package com.example.potel.ui.shopping

import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.potel.ui.theme.TipColor


@Composable
fun CreditCardScreen(
    viewModel: ShopViewModel = viewModel(),
    navController: NavHostController
) {    // 使用 remember 和 mutableStateOf 來保存並更新顯示的文本

    val navRequest by viewModel.navRequest.collectAsState()
    val cardnumber by viewModel.cardnumber.collectAsState()
    val expiredate by viewModel.expiredatenumber.collectAsState()
    val safecode by viewModel.safecodenumber.collectAsState()


    LaunchedEffect(navRequest) {
        Log.d("completeOrder", "navRequest: $navRequest")

        if (navRequest.isNullOrBlank().not()) {
            navController.navigate(navRequest!!)
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(50.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        // 顯示標題
        Text(
            text = "填寫信用卡",
            fontSize = 35.sp, // 字型大小: 35
            fontWeight = FontWeight.ExtraBold, // 字體樣式: 粗體
            color = TipColor.deep_brown, // 字的顏色: 黑色
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
                text = "信用卡卡號為十六碼",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp)
            )
        }


//        Text(text = "MM/YY")
        OutlinedTextField(
            value = expiredate,
            onValueChange = viewModel::onExpiredatenumberChanged,
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
        if (viewModel.expiredatenumberError) {
            Text(
                text = "到期日期為四碼",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp)
            )
        }


        OutlinedTextField(
            value = safecode,
            onValueChange = viewModel::onSafecodenumberChanged,
            label = { Text(text = "CVV") },
            singleLine = true,
            shape = RoundedCornerShape(20.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
        )
        if (viewModel.safecodenumberError) {
            Text(
                text = "安全碼為三碼",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp)
            )
        }


        // 顯示按鈕
        Button(
            onClick = viewModel::onSubmitClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = TipColor.light_brown, // 設定按鈕容器顏色
                contentColor = TipColor.deep_brown // 設定按鈕內容顏色
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