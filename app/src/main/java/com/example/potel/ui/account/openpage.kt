package com.example.potel.ui.account

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext




fun showtoast(message: String, context: Context) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}


fun sendLoginRequestWithEmail(email: String, password: String, context: Context, navController: NavHostController) {

    CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = RetrofitInstance.api.login(LoginRequest(email, password))
            withContext(Dispatchers.Main) {
                if (response.token.isNotEmpty()) {
                    showtoast("登入成功，token: ${response.token}", context)
                    navController.navigate(HOME_NAVIGATION_ROUTE)
                } else {
                    showtoast("登入失敗: ${response.message}", context)
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                showtoast("登入錯誤: ${e.message}", context)
            }
        }
    }
}

fun sendLoginRequestWithPhone(phone: String, password: String, context: Context, navController: NavHostController) {

    CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = RetrofitInstance2.api.login(LoginRequest2(phone, password))
            withContext(Dispatchers.Main) {
                if (response.token.isNotEmpty()) {
                    showtoast("登入成功，token: ${response.token}", context)
                    navController.navigate(HOME_NAVIGATION_ROUTE)
                } else {
                    showtoast("登入失敗: ${response.message}", context)
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                showtoast("登入錯誤: ${e.message}", context)
            }
        }
    }
}

@Composable
fun Login(navController: NavHostController) {
    val context = LocalContext.current

    val input = remember { mutableStateOf("") }
    var inputError by remember { mutableStateOf(false) }

    val emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$".toRegex()

    val phoneRegex = "^[0-9]{10}$".toRegex()

    val password = remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .padding(10.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier

                .padding(10.dp)
        ) {

            Text(
                modifier = Modifier.clickable {
                    navController.navigate(HOME_NAVIGATION_ROUTE)
                },
                text = "Potel",
                fontSize = 40.sp,
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
                    .padding(top = 0.dp)
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
                        showtoast("請填寫所有欄位", context)
                    } else {
                        if (input.value.matches(emailRegex)) {
                            sendLoginRequestWithEmail(
                                input.value,
                                password.value,
                                context,
                                navController
                            )
                        } else if (input.value.matches(phoneRegex)) {
                            sendLoginRequestWithPhone(
                                input.value,
                                password.value,
                                context,
                                navController
                            )
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 0.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "登入", fontSize = 20.sp)
            }
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Button(
                onClick = { navController.navigate("reset_password_route")  },
                modifier = Modifier
                    .width(120.dp)
                    .height(46.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "忘記密碼",
                    fontSize = 20.sp,
                    lineHeight = 46.sp,
                    fontWeight = FontWeight(700),
                    color = Color(0xFF000000),
                )
            }
            Button(
                onClick = { navController.navigate("Signup")  },
                modifier = Modifier
                    .width(120.dp)
                    .height(46.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "會員註冊",
                    fontSize = 20.sp,
                    lineHeight = 46.sp,
                    fontWeight = FontWeight(700),
                    color = Color(0xFF000000),
                    modifier = Modifier
                        .width(120.dp)
                        .height(46.dp)
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
