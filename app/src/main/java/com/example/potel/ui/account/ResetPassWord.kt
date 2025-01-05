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

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun Resetpassword (viewModel:ResetPassWordViewModel = viewModel()
    ,navController: NavHostController) {

    val email = viewModel.email.collectAsState()

    val password = viewModel.password.collectAsState()

    var passwordVisible by remember { mutableStateOf(false) }

    val checkpassword = viewModel.checkpassword.collectAsState()
    var checkpasswordVisible  by remember { mutableStateOf(false) }


    val phonenumber = viewModel.phonenumber.collectAsState()


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
               value = email.value,
               onValueChange = viewModel::onEmailChanged,
               label = { Text("請輸入信箱") },
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
                        value = password.value,
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
                        isError = viewModel.passwordError,
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

                    if (viewModel.passwordError) {
                        Text(
                            text = "密碼需在6 至 20 字符內",
                            color = Color.Red,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }

                    OutlinedTextField(
                        value = checkpassword.value,
                        onValueChange = viewModel::onCheckPasswordChanged,
                        label = { Text(text = "再次確認密碼") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "再次確認密碼"
                            )
                        },
                        trailingIcon = {
                            Text(
                                text = if (checkpasswordVisible) "隱藏" else "顯示",
                                modifier = Modifier.clickable {
                                    checkpasswordVisible = !checkpasswordVisible
                                }
                            )
                        },
                        isError = viewModel.checkpasswordError,
                        shape = RoundedCornerShape(8.dp),
                        visualTransformation = if (checkpasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
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

                    if (viewModel.checkpasswordError) {
                        Text(
                            text = "密碼需輸入相同",
                            color = Color.Red,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }

                    OutlinedTextField(
                        value = phonenumber.value,
                        onValueChange = viewModel::onPhonenumberChanged,
                        label = { Text(text = "請輸入手機號碼") },
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp),
                        isError = viewModel.phonenumberError,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    )
                    if (viewModel.phonenumberError) {
                        Text(
                            text = "手機號碼為十位數字",
                            color = Color.Red,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }



                    Button(
                        onClick = {
                            if (email.value.isEmpty() || password.value.isEmpty() || checkpassword.value.isEmpty()
                                || phonenumber.value.isEmpty() ) {
                                // 顯示錯誤提示
                            } else if (password.value != checkpassword.value) {
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
                        Text(text = "重設密碼", fontSize = 20.sp)
                    }
                }
            }


@Preview(showBackground = true)
@Composable
fun DefaultPreview3() {
     Resetpassword (navController = rememberNavController())
    }