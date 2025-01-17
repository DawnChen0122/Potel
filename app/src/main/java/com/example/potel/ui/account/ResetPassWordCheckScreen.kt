package com.example.potel.ui.account

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.potel.ui.home.AccountScreens
import com.example.potel.ui.theme.TipColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun Resetpassword(
    viewModel: ResetPassWordViewModel = viewModel(), navController: NavHostController
) {

    val email by viewModel.email.collectAsState()

    val passwd by viewModel.passwd.collectAsState()
    var passwdVisible by remember { mutableStateOf(false) }

    val checkpasswd by viewModel.checkpasswd.collectAsState()
    var checkpasswdVisible by remember { mutableStateOf(false) }

    val cellphone by viewModel.cellphone.collectAsState()

    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        verticalArrangement = Arrangement.spacedBy(2.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
//            .border(width = 5.dp, color = Color(0xFF000000))
            .fillMaxSize()
            .background(color = TipColor.light_brown)
            .padding(12.dp)
    )
    {
        Text(
            text = "重設密碼",
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFAA8066)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = viewModel::onEmailChanged,
            label = { Text("請輸入信箱進行確認") },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            isError = viewModel.emailError,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )
        if (viewModel.emailError) {
            Text(
                text = "請輸入有效的信箱",
                color = Color.Red,
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = cellphone,
            onValueChange = viewModel::oncellphoneChanged,
            label = { Text(text = "請輸入手機號碼進行確認") },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            isError = viewModel.cellphoneError,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
        if (viewModel.cellphoneError) {
            Text(
                text = "手機號碼為十位數字",
                color = Color.Red,
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = passwd,
            onValueChange = viewModel::passwdchange,
            label = { Text(text = "請輸入新密碼") },
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
                .padding(top = 10.dp)
        )
        if (viewModel.passwdError) {
            Text(
                text = "密碼需在6至20字內，包含字母數字",
                color = Color.Red,
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = checkpasswd,
            onValueChange = viewModel::checkpasswdchange,
            label = { Text(text = "再次確認新密碼") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "再次確認密碼"
                )
            },
            trailingIcon = {
                Text(
                    text = if (checkpasswdVisible) "隱藏" else "顯示",
                    modifier = Modifier.clickable {
                        checkpasswdVisible = !checkpasswdVisible
                    }
                )
            },
            isError = viewModel.checkpasswdError,
            shape = RoundedCornerShape(8.dp),
            visualTransformation = if (checkpasswdVisible) VisualTransformation.None else PasswordVisualTransformation(),
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
        if (viewModel.checkpasswdError) {
            Text(
                text = "密碼需輸入相同",
                color = Color.Red,
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        errorMessage?.let {
            Text(
                text = it,
                color = Color.Red,
                fontSize = 20.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = {
                if (email.isEmpty() || passwd.isEmpty() || checkpasswd.isEmpty() || cellphone.isEmpty()) {
                    errorMessage = "欄位不得空白"
//                } else if (passwd != checkpasswd) {
//                    errorMessage = "密碼不一致"
                } else {
                    viewModel.viewModelScope.launch {
                        errorMessage = null
                        val result1 = viewModel.checkEmailAndCellphone()
                        if (result1.success) {
                            viewModel.changepasswd()
                            val result2 = viewModel.changepasswd()
                            if (result2.success) {
                                errorMessage = "密碼更改成功"
                                delay(1000)
                                navController.navigate(AccountScreens.Login.name)
                            }
                        } else {
                            errorMessage = "密碼更改失敗"
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFDBC8B6),
            )
        ) {
            Text(
                text = "變更密碼",
                fontSize = 50.sp,
                color = TipColor.deep_brown
            )
        }

    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview3() {
    Resetpassword(navController = rememberNavController())
}