package com.example.potel.ui.account

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.potel.ui.home.HOME_NAVIGATION_ROUTE

@Composable
fun Openpage(navController: NavHostController) {

    val input = remember { mutableStateOf("") }
    var inputError by remember { mutableStateOf(false) }

    val emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$".toRegex()

    val phoneRegex = "^[0-9]{10}$".toRegex()

    val password = remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {

        Text(
            modifier = Modifier.clickable{
                navController.navigate(HOME_NAVIGATION_ROUTE)
            },
            text = "Potel",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Blue
        )

        OutlinedTextField(
            value = input.value,
            onValueChange = {
                input.value = it
                inputError = !(it.matches(emailRegex) || it.matches(phoneRegex))
            },
            label = { Text(text = "請輸入信箱或手機號碼") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            shape = RoundedCornerShape(8.dp),
            isError = inputError,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        )
        if (inputError) {
            Text(
                text = "請輸入有效的信箱或手機號碼",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        OutlinedTextField(
            value = password.value,
            onValueChange = {
                password.value = it
                passwordError = it.isEmpty() || it.length < 6 || it.length > 20
            },
            label = { Text(text = "密碼") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "密碼"
                )
            },
            trailingIcon = {
                Text(
                    text = if (passwordVisible) "隱藏" else "顯示",
                    modifier = Modifier.clickable {
                        passwordVisible = !passwordVisible
                    }
                )
            },
            isError = passwordError,
            shape = RoundedCornerShape(8.dp),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Blue,
                unfocusedIndicatorColor = Color.Gray,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        )

        if (passwordError) {
            Text(
                text = "密碼需在6 至 20 字符內",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        Button(
            onClick = {
                if (input.value.isEmpty() || password.value.isEmpty()) {
                    // 顯示錯誤提示
                } else if (password.value != password.value) {
                    // 顯示密碼不一致的錯誤提示
                } else {
                    // 執行登入邏輯
                    if (input.value.matches(emailRegex)) {
                        // 處理Email登入邏輯
                    } else if (input.value.matches(phoneRegex)) {
                        // 處理手機號碼登入邏輯
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "登入", fontSize = 20.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview6() {
    Openpage(navController = rememberNavController())
}
