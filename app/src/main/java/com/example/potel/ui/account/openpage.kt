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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun Openpage(navController: NavHostController) {

    val email = remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }
    val emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$".toRegex()

    val phonenumber = remember { mutableStateOf("") }
    var phonenumberError by remember { mutableStateOf(false) }

    val password = remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {

        OutlinedTextField(
            value = email.value,
            onValueChange = {
                email.value = it
                emailError = !it.matches(emailRegex)
            },
            label = { Text(text = "請輸入信箱") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            shape = RoundedCornerShape(8.dp),
            isError = emailError,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        )
        if (emailError) {
            Text(
                text = "請輸入有效的信箱",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        OutlinedTextField(
            value = phonenumber.value,
            onValueChange = { phonenumber.value = it
                phonenumberError = it.isEmpty() || it.length != 10 },
            label = { Text(text = "請輸入手機號碼") },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            isError = phonenumberError,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
        if (phonenumberError) {
            Text(
                text = "手機號碼為十位數字",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        OutlinedTextField(
            value = password.value,
            onValueChange = {
                password.value = it
                passwordError = it.isEmpty() || it.length < 6 || it.length > 20 },
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
                if ( email.value.isEmpty() || password.value.isEmpty()) {
                    // 顯示錯誤提示
                } else if (password.value != password.value) {
                    // 顯示密碼不一致的錯誤提示
                } else {
                    "執行註冊邏輯"
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
