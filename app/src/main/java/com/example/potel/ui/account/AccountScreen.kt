package com.example.potel.ui.account

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Signup(viewModel:AccountViewModel = viewModel(), navController: NavHostController) {
//    val items = viewModel.items.collectAsState()

    val  uid = viewModel.uid.collectAsState()
    var uidError by remember { mutableStateOf(false) }

    val email = viewModel.email.collectAsState()
    var emailError by remember { mutableStateOf(false) }
    val emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$".toRegex()


    var inputYear by remember { mutableStateOf("") }
    var inputMonth by remember { mutableStateOf("") }
    var inputDay by remember { mutableStateOf("") }

    val yearRange = (1924..2025).map { it.toString() }
    val monthRange = (1..12).map { it.toString() }
    val dayRange = (1..31).map { it.toString() }

    var selectedYear by remember { mutableStateOf("") }
    var selectedMonth by remember { mutableStateOf("") }
    var selectedDay by remember { mutableStateOf("") }

    var expandedYear by remember { mutableStateOf(false) }
    var expandedMonth by remember { mutableStateOf(false) }
    var expandedDay by remember { mutableStateOf(false) }

    val filteredYears = yearRange.filter { it.startsWith(inputYear, true) }
    val filteredMonths = monthRange.filter { it.startsWith(inputMonth, true) }
    val filteredDays = dayRange.filter { it.startsWith(inputDay, true) }

    var inputGender by remember { mutableStateOf("") }
    val genderRange = listOf("男", "女","不願透漏").map{ it.toString() }

    var selectedGender by remember { mutableStateOf("") }
    var expandedGender by remember { mutableStateOf(false) }

    val filteredGenders = genderRange.filter { it.startsWith(inputGender, true) }



    val  password = viewModel.password.collectAsState()
    var passwordVisible by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }

    var checkpassword = viewModel.checkpassword.collectAsState()
    var checkpasswordVisible  by remember { mutableStateOf(false) }
    var checkpasswordError by remember { mutableStateOf(false) }


    val  username = viewModel.username.collectAsState()


    val  phonenumber = viewModel.phonenumber.collectAsState()
    var phonenumberError by remember { mutableStateOf(false) }


    val  address = viewModel.address.collectAsState()





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
//            uidError = uid.value.isEmpty(),
            label = { Text(text = "請輸入用戶名稱") },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            isError = uidError,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )
        if(uidError) {
            Text(
                text = "用戶名稱為必填欄位",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp)
            )
        }


        OutlinedTextField(
            value = email.value.firstOrNull()?.title ?: "",
            onValueChange = viewModel::onEmailChanged,
//            onValueChange = {
//                email.value = it
//                emailError = !it.matches(emailRegex) },
            label = { Text("請輸入信箱") },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            isError = emailError,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )
        if (emailError) {
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
        ) {
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
                    filteredYears.forEach { yearRange ->
                        DropdownMenuItem(
                            text = { Text(yearRange) },
                            onClick = {
                                selectedYear = yearRange
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
                        expandedMonth= true
                    },
                    singleLine = true,
                    label = { Text("月") },
                    trailingIcon = { TrailingIcon(expanded = expandedMonth) }
                )
                ExposedDropdownMenu(
                    expanded = expandedMonth,
                    onDismissRequest = { expandedMonth = false }
                ) {
                    filteredMonths.forEach { monthRange ->
                        DropdownMenuItem(
                            text = { Text(monthRange) },
                            onClick = {
                                selectedMonth = monthRange
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
                        inputDay= it
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
                    filteredDays.forEach { dayRange ->
                        DropdownMenuItem(
                            text = { Text(dayRange) },
                            onClick = {
                                selectedDay = dayRange
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
                modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryEditable, true)
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
                filteredGenders.forEach { genderRange ->
                    DropdownMenuItem(
                        text = { Text(genderRange) },
                        onClick = {
                            selectedGender = genderRange
                            inputGender = genderRange
                            expandedGender = false
                        }
                    )
                }
            }
        }


        OutlinedTextField(
            value = password.value.firstOrNull()?.title ?: "",
            onValueChange = viewModel::onPasswordChanged,
//            onValueChange = {
//                password.value = it
//                passwordError = it.isEmpty() || it.length < 6 || it.length > 20 },
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
                .padding(top = 16.dp)
        )
        if (passwordError) {
            Text(
                text = "密碼需在6 至 20 字符內",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp)
            )
        }




        OutlinedTextField(
            value = checkpassword.value.firstOrNull()?.title ?: "",
            onValueChange = viewModel::onCheckPasswordChanged,
//            onValueChange = { checkpassword.value = it
//                checkpasswordError = it != password.value},
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
            isError = checkpasswordError,
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

        if (checkpasswordError) {
            Text(
                text = "密碼需輸入相同",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        OutlinedTextField(
            value = username.value.firstOrNull()?.title ?: "",
            onValueChange = viewModel::onUsernameChanged,
            label = { Text(text = "請輸入姓名") },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        )



        OutlinedTextField(
            value = phonenumber.value.firstOrNull()?.title ?: "",
            onValueChange = viewModel::onPhonenumberChanged,
//            onValueChange = { phonenumber.value = it
//                phonenumberError = it.isEmpty() || it.length != 10 },
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
            value = address.value.firstOrNull()?.title ?: "",
            onValueChange = viewModel::onAddressumberChanged,
//            onValueChange = { phonenumber.value = it
//                phonenumberError = it.isEmpty() || it.length != 10 },
            label = { Text(text = "請輸入地址") },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            isError = phonenumberError,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )


        // 註冊按鈕
        Button(
            onClick = {
                if (uid.value.isEmpty() || email.value.isEmpty() || password.value.isEmpty() || checkpassword.value.isEmpty()
                    || username.value.isEmpty() || phonenumber.value.isEmpty() || address.value.isEmpty() ) {
                    // 顯示錯誤提示
                } else if (password.value != checkpassword.value) {
                    // 顯示密碼不一致的錯誤提示
                } else {
                    "執行註冊邏輯"
                }
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
    Signup(navController = rememberNavController())
}
