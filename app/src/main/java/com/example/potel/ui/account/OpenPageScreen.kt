package com.example.potel.ui.account

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import com.example.potel.ui.theme.TipColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable


fun Login(
    viewModel: OpenpageViewModel = viewModel(), navController: NavHostController
) {


    val context = LocalContext.current

    val preferences = context.getSharedPreferences("member", Context.MODE_PRIVATE)

    val inputError by viewModel.inputError.collectAsState()

    val passwd by viewModel.passwd.collectAsState()

    var passwdVisible by remember { mutableStateOf(false) }

    var currentInput by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf<String?>(null) }


    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,

        modifier = Modifier
//            .border(width = 5.dp, color = Color(0xFF000000))
//            .padding(12.dp)
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = TipColor.light_brown)
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
                color = Color(0xFFAA8066)
            )


            OutlinedTextField(
                value = currentInput,
                onValueChange = { value ->
                    currentInput = value
                    viewModel.onInputChanged(value)
                },
                label = { Text(text = "請輸入信箱或手機號碼") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
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
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp)
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
                    text = "密碼需在6至20字內，包含字母數字",
                    color = Color.Red,
                    fontSize = 20.sp,
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

            Button(
                onClick = {
                    if (currentInput.isEmpty()) {
                        errorMessage = "信箱或手機號碼欄位不得空白"
                    } else if (!currentInput.matches(viewModel.emailRegex) && !currentInput.matches(
                            viewModel.cellphoneRegex
                        )
                    ) {
                        errorMessage = "請輸入有效的信箱或手機號碼"
                    } else {

                        errorMessage = null

                        val inputlog = Inputlog(currentInput, passwd)

                        viewModel.viewModelScope.launch {
                            try {
                                val member = viewModel.login(inputlog)
                                Log.d("Login", "已登入0，issucc=$member")
                                if (member != null && member.memberid != 0) {

                                    preferences.edit().putString(
                                        "memberid",
                                        member.memberid.toString()
                                    )
                                        .putString("name", member.name)
                                        .putString("passwd", member.passwd)
                                        .putString("cellphone", member.cellphone)
                                        .putString("address", member.address)
                                        .putString("email", member.email)
                                        .putString("gender", member.gender)
                                        .putString("birthday", member.birthday)
                                        .apply()
                                    navController.navigate(AccountScreens.HomeRoute.name)
                                } else {
                                    Log.d("Login", "登入失敗，錯誤訊息: 登入失敗，請檢查您的帳號密碼")
                                    errorMessage = "登入失敗，請檢查您的帳號密碼"
                                }
                            } catch (e: Exception) {

                                Log.e("Login", "登入過程中出現錯誤: ${e.message}")
                                errorMessage = "登入失敗，請檢查您的帳號密碼"
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFDBC8B6),
                )
            ) {
                Text(text = "登入", fontSize = 50.sp
                       ,color = TipColor.deep_brown
                )

            }

        }

        errorMessage?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = it,
                color = Color.Red,
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 16.dp)
            )
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
                    color = TipColor.deep_brown,
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

            Spacer(modifier = Modifier.width(20.dp))

            Text(
                text = "忘記密碼",
                style = TextStyle(
                    fontSize = 33.sp,
                    lineHeight = 32.sp,
                    fontWeight = FontWeight(700),
                    color = TipColor.deep_brown,
                    textAlign = TextAlign.Center,
                    textDecoration = TextDecoration.Underline
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
