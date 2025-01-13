package com.example.potel.ui.account

import android.annotation.SuppressLint
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
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
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

    val  cellphone by viewModel. cellphone.collectAsState()



    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Text(
            text = "重設密碼",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Blue
        )


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
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp)
            )
        }



        OutlinedTextField(
            value =  cellphone,
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
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp)
            )
        }



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
                text = "密碼需在6 至 20 字內數字英文至少各一",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp)
            )
        }


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
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp)
            )
        }




        Button(
            onClick = {
                if (email.isEmpty() || passwd.isEmpty() || checkpasswd.isEmpty()
                    ||  cellphone.isEmpty()
                ) {
                    "欄位不得空白"
                } else if (passwd != checkpasswd) {
                    "密碼不一致"
                } else {
                    viewModel.viewModelScope.launch {
                        viewModel.checkEmailAndCellphone()
                        viewModel.changepasswd()
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "重設密碼", fontSize = 20.sp)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview3() {
    Resetpassword(navController = rememberNavController())
}