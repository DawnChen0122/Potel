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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun Register(viewModel:AccountViewModel = viewModel(), navController: NavHostController) {
//    val items = viewModel.items.collectAsState()
    val  uid = viewModel.uid.collectAsState()

    val email = viewModel.email.collectAsState()

    val  password = viewModel.password.collectAsState()


    val  username = viewModel.username.collectAsState()

    val  phonenumber = viewModel.phonenumber.collectAsState()

    val  address = viewModel.address.collectAsState()


    var passwordVisible by remember { mutableStateOf(false) }




    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "會員註冊",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Blue
        )


        OutlinedTextField(
            value = uid.value.firstOrNull()?.title ?: "",
            onValueChange = viewModel::onUidChanged,
            label = { Text(text = "請輸入用戶名稱") },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )


        OutlinedTextField(
            value = email.value.firstOrNull()?.title ?: "",
            onValueChange = viewModel::onEmailChanged,
            label = { Text("請輸入信箱") },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )

        OutlinedTextField(
            value = password.value.firstOrNull()?.title ?: "",
            onValueChange = viewModel::onPasswordChanged,
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
                .padding(top = 16.dp)
        )

        OutlinedTextField(
            value = username.value.firstOrNull()?.title ?: "",
            onValueChange = viewModel::onUsernameChanged,
            label = { Text(text = "請輸入姓名") },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )


        OutlinedTextField(
            value = phonenumber.value.firstOrNull()?.title ?: "",
            onValueChange = viewModel::onPhonenumberChanged,
            label = { Text(text = "請輸入手機號碼") },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )

        OutlinedTextField(
            value = address.value.firstOrNull()?.title ?: "",
            onValueChange = viewModel::onAddressumberChanged,
            label = { Text(text = "請輸入地址") },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )


        // 註冊按鈕
        Button(
            onClick = {
                //
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "註冊", fontSize = 16.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview1() {
    Register(navController = rememberNavController())
}
