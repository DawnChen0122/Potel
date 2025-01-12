//package com.example.potel.ui.account
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.input.PasswordVisualTransformation
//import androidx.compose.ui.text.input.VisualTransformation
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.rememberNavController
//import androidx.compose.material3.Icon
//import androidx.compose.material3.Text
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowDropDown
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.window.Popup
//
//@Composable
//fun Edit(viewModel: EditAccountViewModel = viewModel(), navController: NavHostController) {
//    val uid by viewModel.uid.collectAsState()
//    val email by viewModel.email.collectAsState()
//    val password by viewModel.password.collectAsState()
//    val checkpassword by viewModel.checkpassword.collectAsState()
//    val username by viewModel.username.collectAsState()
//    val phonenumber by viewModel.phonenumber.collectAsState()
//    val address by viewModel.address.collectAsState()
//    val gender by viewModel.gender.collectAsState()
//    val birthdate by viewModel.birthDate.collectAsState()
//    val errorMessage = viewModel.errorMessage
//
//    // 欄位驗證錯誤訊息
//    val isFormValid = uid.isNotEmpty() && email.isNotEmpty() && password == checkpassword &&
//            password.isNotEmpty() && username.isNotEmpty() && phonenumber.isNotEmpty() && address.isNotEmpty()
//
//    Column(modifier = Modifier.padding(16.dp)) {
//        CustomOutlinedTextField(
//            value = uid,
//            onValueChange = viewModel::onUidChanged,
//            label = "用戶 ID",
//            isError = uid.isEmpty(),
//        )
//        CustomOutlinedTextField(
//            value = email,
//            onValueChange = viewModel::onEmailChanged,
//            label = "電子郵件",
//            isError = email.isEmpty(),
//        )
//        CustomOutlinedTextField(
//            value = password,
//            onValueChange = viewModel::onPasswordChanged,
//            label = "密碼",
//            isError = password.isEmpty(),
//            visualTransformation = PasswordVisualTransformation()
//        )
//        CustomOutlinedTextField(
//            value = checkpassword,
//            onValueChange = viewModel::onCheckPasswordChanged,
//            label = "確認密碼",
//            isError = checkpassword != password,
//            visualTransformation = PasswordVisualTransformation()
//        )
//        CustomOutlinedTextField(
//            value = username,
//            onValueChange = viewModel::onUsernameChanged,
//            label = "姓名",
//            isError = username.isEmpty(),
//        )
//        CustomOutlinedTextField(
//            value = phonenumber,
//            onValueChange = viewModel::onPhoneNumberChanged,
//            label = "電話",
//            isError = phonenumber.isEmpty(),
//        )
//        CustomOutlinedTextField(
//            value = address,
//            onValueChange = viewModel::onAddressChanged,
//            label = "地址",
//            isError = address.isEmpty(),
//        )
//
//        // 生日選擇
//        Text("請選擇生日", modifier = Modifier.padding(top = 16.dp))
//        Row {
//            BirthDatePicker(
//                selectedDate = birthdate,
//                onDateChanged = viewModel::onBirthDateChanged
//            )
//        }
//
//        // 性別選擇
//        Text("請選擇性別", modifier = Modifier.padding(top = 16.dp))
//        GenderPicker(
//            selectedGender = gender,
//            onGenderChanged = viewModel::onGenderChanged
//        )
//
//        // 顯示錯誤訊息
//        errorMessage?.let {
//            Text(text = it, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
//        }
//
//        // 提交按鈕
//        Button(
//            onClick = {
//                if (isFormValid) {
//                    // 執行表單提交邏輯
//                    // viewModel.submitForm()
//                } else {
//                    viewModel.onError("請填寫所有欄位")
//                }
//            },
//            enabled = isFormValid,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 16.dp)
//        ) {
//            Text("提交", fontSize = 16.sp)
//        }
//    }
//}
//
//@Composable
//fun BirthDatePicker(selectedDate: String, onDateChanged: (String) -> Unit) {
//    var selectedYear by remember { mutableStateOf("") }
//    var selectedMonth by remember { mutableStateOf("") }
//    var selectedDay by remember { mutableStateOf("") }
//
//    var expandedYear by remember { mutableStateOf(false) }
//    var expandedMonth by remember { mutableStateOf(false) }
//    var expandedDay by remember { mutableStateOf(false) }
//
//    val years = (1924..2025).map { it.toString() }
//    val months = (1..12).map { it.toString().padStart(2, '0') }
//    val days = (1..31).map { it.toString().padStart(2, '0') }
//
//    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//        DropdownMenuBox(
//            label = "年",
//            expanded = expandedYear,
//            options = years,
//            onSelect = { selectedYear = it },
//            onExpandedChange = { expandedYear = it }
//        )
//        DropdownMenuBox(
//            label = "月",
//            expanded = expandedMonth,
//            options = months,
//            onSelect = { selectedMonth = it },
//            onExpandedChange = { expandedMonth = it }
//        )
//        DropdownMenuBox(
//            label = "日",
//            expanded = expandedDay,
//            options = days,
//            onSelect = { selectedDay = it },
//            onExpandedChange = { expandedDay = it }
//        )
//    }
//
//    if (selectedYear.isNotEmpty() && selectedMonth.isNotEmpty() && selectedDay.isNotEmpty()) {
//        val fullDate = "$selectedYear/$selectedMonth/$selectedDay"
//        onDateChanged(fullDate)
//    }
//}
//
//@Composable
//fun DropdownMenuBox(
//    label: String,
//    expanded: Boolean,
//    options: List<String>,
//    onSelect: (String) -> Unit,
//    onExpandedChange: (Boolean) -> Unit
//) {
//    var selectedOption by remember { mutableStateOf("") }
//
//    Column(modifier = Modifier.fillMaxWidth()) {
//        TextField(
//            readOnly = true,
//            value = selectedOption,
//            onValueChange = {},
//            label = { Text(label) },
//            trailingIcon = { Icon(Icons.Default.ArrowDropDown, contentDescription = "Select") },
//            modifier = Modifier
//                .fillMaxWidth()
//                .clickable { onExpandedChange(!expanded) }
//        )
//
//        if (expanded) {
//            Popup(
//                alignment = Alignment.TopStart,
//                onDismissRequest = { onExpandedChange(false) }
//            ) {
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .background(Color.White, shape = RoundedCornerShape(8.dp))
//                        .padding(8.dp)
//                ) {
//                    options.forEach { option ->
//                        Text(
//                            text = option,
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .clickable {
//                                    selectedOption = option
//                                    onSelect(option)
//                                    onExpandedChange(false)
//                                }
//                                .padding(8.dp),
//                            style = MaterialTheme.typography.bodyLarge
//                        )
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun GenderPicker(selectedGender: String, onGenderChanged: (String) -> Unit) {
//    val genderOptions = listOf("男", "女", "不願透露")
//    var expanded by remember { mutableStateOf(false) }
//
//    Box(modifier = Modifier.fillMaxWidth()) {
//        TextField(
//            readOnly = true,
//            value = selectedGender,
//            onValueChange = {},
//            label = { Text("性別") },
//            trailingIcon = {
//                Icon(
//                    Icons.Default.ArrowDropDown,
//                    contentDescription = "Select Gender"
//                )
//            },
//            modifier = Modifier
//                .fillMaxWidth()
//                .clickable { expanded = !expanded }
//        )
//
//        if (expanded) {
//            Popup(
//                alignment = Alignment.TopStart,
//                onDismissRequest = { expanded = false }
//            ) {
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .background(Color.White)
//                        .border(1.dp, Color.Gray)
//                ) {
//                    genderOptions.forEach { gender ->
//                        Text(
//                            text = gender,
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .clickable {
//                                    onGenderChanged(gender)
//                                    expanded = false
//                                }
//                                .padding(8.dp)
//                        )
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun CustomOutlinedTextField(
//    value: String,
//    onValueChange: (String) -> Unit,
//    label: String,
//    isError: Boolean,
//    visualTransformation: VisualTransformation = VisualTransformation.None,
//    modifier: Modifier = Modifier
//) {
//    OutlinedTextField(
//        value = value,
//        onValueChange = onValueChange,
//        label = { Text(label) },
//        isError = isError,
//        visualTransformation = visualTransformation,
//        modifier = modifier.fillMaxWidth(),
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview123() {
//    Edit(navController = rememberNavController())
//}
