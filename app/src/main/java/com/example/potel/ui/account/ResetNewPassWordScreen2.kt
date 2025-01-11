//package com.example.potel.ui.account
//
//import android.annotation.SuppressLint
//import android.util.Log
//
//import androidx.annotation.OptIn;
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Lock
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.text.input.PasswordVisualTransformation
//import androidx.compose.ui.text.input.VisualTransformation
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewModelScope
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.rememberNavController
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.launch
//
//
//@SuppressLint("SuspiciousIndentation")
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//
//fun Resetpassword2 (viewModel:ResetPassWordViewModel = viewModel()
//                    ,navController: NavHostController ) {
//
//
//
//
//
//    val passwd by viewModel.passwd.collectAsState()
//    var passwordVisible by remember { mutableStateOf(false) }
//
//    val checkpassword by viewModel.checkpassword.collectAsState()
//    var checkpasswordVisible  by remember { mutableStateOf(false) }
//
//
//    val email by viewModel.email.collectAsState()
//
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(10.dp)
//    ) {
//        Text(
//            text = "重設密碼",
//            fontSize = 20.sp,
//            fontWeight = FontWeight.Bold,
//            color = Color.Blue
//        )
//
//
//
//        OutlinedTextField(
//            value = passwd,
//            onValueChange = viewModel::onPasswordChanged,
//            label = { Text(text = "密碼") },
//            leadingIcon = {
//                Icon(
//                    imageVector = Icons.Default.Lock,
//                    contentDescription = "密碼"
//                )
//            },
//            trailingIcon = {
//                Text(
//                    text = if (passwordVisible) "隱藏" else "顯示",
//                    modifier = Modifier.clickable {
//                        passwordVisible = !passwordVisible
//                    }
//                )
//            },
//            isError = viewModel.passwordError,
//            shape = RoundedCornerShape(8.dp),
//            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
//            singleLine = true,
//            colors = TextFieldDefaults.colors(
//                focusedIndicatorColor = Color.Blue,
//                unfocusedIndicatorColor = Color.Gray,
//            ),
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 10.dp)
//        )
//        if (viewModel.passwordError) {
//            Text(
//                text = "密碼需在6 至 20 字內數字英文至少各一",
//                color = Color.Red,
//                fontSize = 12.sp,
//                modifier = Modifier.padding(start = 16.dp)
//            )
//        }
//
//
//        OutlinedTextField(
//            value = checkpassword,
//            onValueChange = viewModel::onCheckPasswordChanged,
//            label = { Text(text = "再次確認密碼") },
//            leadingIcon = {
//                Icon(
//                    imageVector = Icons.Default.Lock,
//                    contentDescription = "再次確認密碼"
//                )
//            },
//            trailingIcon = {
//                Text(
//                    text = if (checkpasswordVisible) "隱藏" else "顯示",
//                    modifier = Modifier.clickable {
//                        checkpasswordVisible = !checkpasswordVisible
//                    }
//                )
//            },
//            isError = viewModel.checkpasswordError,
//            shape = RoundedCornerShape(8.dp),
//            visualTransformation = if (checkpasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
//            singleLine = true,
//            colors = TextFieldDefaults.colors(
//                focusedIndicatorColor = Color.Blue,
//                unfocusedIndicatorColor = Color.Gray,
//            ),
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 10.dp)
//        )
//        if (viewModel.checkpasswordError) {
//            Text(
//                text = "密碼需輸入相同",
//                color = Color.Red,
//                fontSize = 12.sp,
//                modifier = Modifier.padding(start = 16.dp)
//            )
//        }
//
//
//        Button(
//            onClick = {
//                if ( passwd.isEmpty() || checkpassword.isEmpty()) {
//                    "欄位不得空白"
//                } else if (passwd != checkpassword) {
//                    "密碼不一致"
//                } else {
//                    viewModel.viewModelScope.launch {
//                        val member = Member(email = email ,passwd = passwd)
//
//                        viewModel.ChangePassWord(member)
//                    }
//                }
//            },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 10.dp),
//            shape = RoundedCornerShape(8.dp)
//        ) {
//            Text(text = "重設密碼", fontSize = 20.sp)
//        }
//    }
//}
//
//
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview333() {
//    Resetpassword2 (navController = rememberNavController())
//}