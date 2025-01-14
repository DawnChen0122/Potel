package com.example.potel.ui.account

import android.content.Context
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.material3.Text
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.potel.ui.home.AccountScreens
import kotlinx.coroutines.launch
import androidx.compose.ui.text.style.TextDecoration


@OptIn(ExperimentalMaterial3Api::class)
@Composable


fun Login(
    viewModel: OpenpageViewModel = viewModel(), navController: NavHostController
) {

    val context = LocalContext.current

    val preferences = context.getSharedPreferences("member", Context.MODE_PRIVATE)


    val inputError by viewModel.inputError.collectAsState()

    val cellphone by viewModel.cellphone.collectAsState()


    val email by viewModel.email.collectAsState()

    val passwd by viewModel.passwd.collectAsState()

    var passwdVisible by remember { mutableStateOf(false) }

    var currentInput by remember { mutableStateOf("") }


    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .border(width = 5.dp, color = Color(0xFF000000))
            .padding(5.dp)
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = Color(0xFFF7E3A6))
            .padding(top = 12.dp, bottom = 12.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier

                .padding(10.dp)
        ) {
            Text(

                text = "Potel",
                fontSize = 100.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFFD700)
            )


            OutlinedTextField(
                value = currentInput,
                onValueChange = { value ->
                    currentInput = value
                    viewModel.onInputChanged(value)
                },
                label = { Text(text = "請輸入信箱或手機號碼") },
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
                value = passwd,
                onValueChange = viewModel::passwdchange,
                label = { Text(text = "密碼") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "密碼"
                    )
                },
                trailingIcon = {
                    Text(
                        text = if (passwdVisible) "隱藏" else "顯示",
                        modifier = Modifier.clickable {
                            passwdVisible = !passwdVisible
                        }
                    )
                },
                isError = viewModel.passwdError,
                shape = RoundedCornerShape(8.dp),
                visualTransformation = if (passwdVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Blue,
                    unfocusedIndicatorColor = Color.Gray,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
            if (viewModel.passwdError) {
                Text(
                    text = "密碼需在6至20字符內，且包含字母和數字",
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
        )

        {

            var errorMessage by remember { mutableStateOf<String?>(null) }
            // 註冊按鈕
            Button(
                onClick = {
                    if (email.isEmpty() && cellphone.isEmpty()) {
                        errorMessage = "信箱或手機號碼欄位不得空白"
                    } else if (!email.matches(viewModel.emailRegex) && !cellphone.matches(
                            viewModel.cellphoneRegex
                        )
                    ) {
                        errorMessage = "請輸入有效的信箱或手機號碼"
                    } else {
                        errorMessage = null // 清除錯誤訊息
                        val inputlog = Inputlog(currentInput, passwd)
                        viewModel.viewModelScope.launch {
                            val member = viewModel.login(inputlog)
                            Log.d("Login", "已登入0，issucc=$member")

                            if (member.memberid != 0) {  // 判斷登入是否成功（假設成功的 memberid 會非 0）
                                preferences.edit().putInt("memberid", member.memberid)
                                    .putString("name", member.name)
                                    .putString("passwd", member.passwd)
                                    .putString("cellphone", member.cellphone)
                                    .putString("address", member.address)
                                    .putString("email", member.email)
                                    .putString("gender", member.gender)
                                    .putString("birthday", member.birthday)
                                    .apply()
                                Log.d("Login", "已登入1，輸入的信箱/手機號碼: $member")
                                navController.navigate(AccountScreens.HomeRoute.name)
                                Log.d("Login", "已登入2，輸入的信箱/手機號碼: $inputlog")
                            } else {
                                // 登入失敗，顯示錯誤訊息
                                Log.d("Login", "登入失敗，錯誤訊息: 登入失敗，請檢查您的帳號密碼")
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFA500),)
            ) {
                Text(text = "登入", fontSize = 50.sp)
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



        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = "註冊",
                style = TextStyle(
                    fontSize = 33.sp,
                    lineHeight = 32.sp,
                    fontWeight = FontWeight(700),
                    color  = Color.White,
                    textAlign = TextAlign.Center,
                    textDecoration = TextDecoration.Underline
                ),
                modifier = Modifier
                    .width(135.dp)
                    .height(45.dp)
                    .clickable {
                        navController.navigate(route = AccountScreens.Signup.name)
                    }
            )

            Text(
                text = "忘記密碼",
                style = TextStyle(
                    fontSize = 33.sp,
                    lineHeight = 32.sp,
                    fontWeight = FontWeight(700),
                    color  = Color.White,
                    textAlign = TextAlign.Center,
                    textDecoration = TextDecoration.Underline // 加上底線
                ),
                modifier = Modifier
                    .width(135.dp)
                    .height(45.dp)
                    .clickable {
                        navController.navigate(route = AccountScreens.Reset.name)
                    }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview6() {
    Login(navController = rememberNavController())
}
