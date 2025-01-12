package com.example.potel.ui.account

import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun Signup(
    viewModel: AccountViewModel = viewModel(),
    navController: NavHostController
) {

    val name by viewModel.name.collectAsState()

    val email by viewModel.email.collectAsState()

    var inputYear by remember { mutableStateOf("") }
    val yearRange = (1924..2025).map { it.toString() }
    var expandedYear by remember { mutableStateOf(false) }

    var inputMonth by remember { mutableStateOf("") }
    val monthRange = (1..12).map { it.toString() }
    var expandedMonth by remember { mutableStateOf(false) }

    var inputDay by remember { mutableStateOf("") }
    val dayRange = (1..31).map { it.toString() }
    var expandedDay by remember { mutableStateOf(false) }

    var inputGender by remember { mutableStateOf("") }
    val genderRange = listOf("男", "女", "不願透漏").map { it.toString() }
    var expandedGender by remember { mutableStateOf(false) }

    val Gender by viewModel.Gender.collectAsState()


    val password by viewModel.password.collectAsState()
    var passwordVisible by remember { mutableStateOf(false) }

    val checkpassword by viewModel.checkpassword.collectAsState()
    var checkpasswordVisible by remember { mutableStateOf(false) }


    val phonenumber by viewModel.phonenumber.collectAsState()
    var phonenumberError by remember { mutableStateOf(false) }

    val address by viewModel.address.collectAsState()

    val scrollState = rememberScrollState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .verticalScroll(scrollState)
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
            value = name,
            onValueChange = viewModel::onnameChanged,
            label = { Text(text = "請輸入姓名") },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        )


        OutlinedTextField(
            value = email,
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


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .background(Color.White, RoundedCornerShape(8.dp))
        ) {
            Text(
                text = "請選擇出生年月日",
                modifier = Modifier.padding(10.dp)
            )
        }


        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.Top
        )
        {
            ExposedDropdownMenuBox(
                expanded = expandedYear,
                onExpandedChange = { expandedYear = it },
                modifier = Modifier.weight(1f)
            ) {
                TextField(
                    readOnly = true,
                    modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryEditable, true),
                    value = inputYear,
                    onValueChange = {
                        inputYear = it
                        expandedYear = true
                    },
                    singleLine = true,
                    label = { Text("年") },
                    trailingIcon = { TrailingIcon(expanded = expandedYear) }
                )
                ExposedDropdownMenu(
                    expanded = expandedYear,
                    onDismissRequest = { expandedYear = false }
                ) {
                    yearRange.forEach { yearRange ->
                        DropdownMenuItem(
                            text = { Text(yearRange) },
                            onClick = {
                                inputYear = yearRange
                                expandedYear = false
                            }
                        )
                    }
                }
            }

            ExposedDropdownMenuBox(
                expanded = expandedMonth,
                onExpandedChange = { expandedMonth = it },
                modifier = Modifier.weight(1f)
            ) {
                TextField(
                    readOnly = true,
                    modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryEditable, true),
                    value = inputMonth,
                    onValueChange = {
                        inputMonth = it
                        expandedMonth = true
                    },
                    singleLine = true,
                    label = { Text("月") },
                    trailingIcon = { TrailingIcon(expanded = expandedMonth) }
                )
                ExposedDropdownMenu(
                    expanded = expandedMonth,
                    onDismissRequest = { expandedMonth = false }
                ) {
                    monthRange.forEach { monthRange ->
                        DropdownMenuItem(
                            text = { Text(monthRange) },
                            onClick = {
                                inputMonth = monthRange
                                expandedMonth = false
                            }
                        )
                    }
                }
            }


            ExposedDropdownMenuBox(
                expanded = expandedDay,
                onExpandedChange = { expandedDay = it },
                modifier = Modifier.weight(1f)
            ) {
                TextField(
                    readOnly = true,
                    modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryEditable, true),
                    value = inputDay,
                    onValueChange = {
                        inputDay = it
                        expandedDay = true
                    },
                    singleLine = true,
                    label = { Text("日") },
                    trailingIcon = { TrailingIcon(expanded = expandedDay) }
                )
                ExposedDropdownMenu(
                    expanded = expandedDay,
                    onDismissRequest = { expandedDay = false }
                ) {
                    dayRange.forEach { dayRange ->
                        DropdownMenuItem(
                            text = { Text(dayRange) },
                            onClick = {
                                inputDay = dayRange
                                expandedDay = false
                            }
                        )
                    }
                }
            }
        }


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .background(Color.White, RoundedCornerShape(8.dp))
        )
        {
            Text(
                text = "請選擇性別",
                modifier = Modifier.padding(10.dp)
            )
        }


        ExposedDropdownMenuBox(
            expanded = expandedGender,
            onExpandedChange = { expandedGender = it },
            modifier = Modifier.fillMaxWidth()
        )
        {
            TextField(
                readOnly = true,
                modifier = Modifier
                    .menuAnchor(MenuAnchorType.PrimaryEditable, true)
                    .fillMaxWidth(),
                value = inputGender,
                onValueChange = {
                    inputGender = it
                    expandedGender = true
                },
                singleLine = true,
                label = { Text("性別") },
                trailingIcon = { TrailingIcon(expanded = expandedGender) }
            )

            ExposedDropdownMenu(
                expanded = expandedGender,
                onDismissRequest = { expandedGender = false }
            ) {
                genderRange.forEach { genderOption ->
                    DropdownMenuItem(
                        text = { Text(genderOption) },
                        onClick = {
                            inputGender = genderOption
                            expandedGender = false
                            viewModel.onGenderChanged(genderOption)
                        }
                    )
                }
            }
        }


        OutlinedTextField(
            value = password,
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
                .padding(top = 16.dp)
        )
        if (viewModel.passwordError) {
            Text(
                text = "密碼需在6至20字符內，且包含字母和數字",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp)
            )
        }


        OutlinedTextField(
            value = checkpassword,
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
            value = phonenumber,
            onValueChange = viewModel::onPhonenumberChanged,
            label = { Text(text = "請輸入手機號碼") },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
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
            value = address,
            onValueChange = viewModel::onAddressChanged,
            label = { Text(text = "請輸入地址") },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )


        var errorMessage by remember { mutableStateOf<String?>(null) }
        // 註冊按鈕
        Button(
            onClick = {

                if (email.isEmpty() || password.isEmpty() || checkpassword.isEmpty()
                    || name.isEmpty() || phonenumber.isEmpty() || address.isEmpty()
                ) {
                    errorMessage = "欄位不得空白"
                } else if (password != checkpassword) {
                    errorMessage = "密碼與確認密碼不同"
                } else {
                    viewModel.viewModelScope.launch {
                        val member = Member(
                            name = name,
                            email = email,
                            gender = Gender.toString(),
                            passwd = password,
                            cellphone = phonenumber,
                            address = address,

                            )
                        viewModel.addmember(member)
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "註冊", fontSize = 16.sp)
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
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview1() {
    Signup(navController = rememberNavController())
}
