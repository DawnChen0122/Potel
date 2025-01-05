package com.example.potel.ui.account

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.potel.ui.home.HOME_NAVIGATION_ROUTE
import androidx.compose.material3.Text
import androidx.lifecycle.viewmodel.compose.viewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun Login(viewModel:OpenpageViewModel = viewModel()
          , navController: NavHostController) {

    val inputError by viewModel.inputError.collectAsState()
    val phonenumber = viewModel.phonenumber.collectAsState()
    val email = viewModel.email.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier

                .padding(10.dp)
        ) {
            Text(
                modifier = Modifier.clickable {
                    navController.navigate(HOME_NAVIGATION_ROUTE)
                },
                text = "Potel" ,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Blue
            )

            OutlinedTextField(
                value = if (email.value.isNotEmpty()) email.value else phonenumber.value,
                onValueChange = { newValue ->
                    viewModel.onInputChanged(newValue)
                },
                label = { Text(text = "請輸入信箱或手機號碼") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                shape = RoundedCornerShape(8.dp),
                isError = inputError,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 0.dp)
            )
            if (inputError) {
                Text(
                    text = "請輸入有效的信箱或手機號碼",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier

                .padding(10.dp)
        ) {
            var errorMessage by remember { mutableStateOf<String?>(null) }
            // 註冊按鈕
            Button(
                onClick = {
                    if (email.value.isEmpty() && phonenumber.value.isEmpty()) {
                        errorMessage = "信箱或手機號碼欄位不得空白"
                    } else if (!email.value.matches(viewModel.emailRegex) && !phonenumber.value.matches(viewModel.phonenumberRegex)) {
                        errorMessage = "請輸入有效的信箱或手機號碼"
                    } else {
                        errorMessage = null // 清除錯誤訊息
                        // 執行註冊邏輯
                        "執行註冊邏輯"
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "註冊", fontSize = 16.sp)
            }
            errorMessage?.let {
                Text(
                    text = it,
                    color = Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview6() {
    Login(navController = rememberNavController())
}
