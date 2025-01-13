package com.example.potel.ui.account

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Lock
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Edit(navController: NavHostController) {

    // 假設從資料庫中讀取的資料
    val defaultUid = "user123"
    val defaultEmail = "user@example.com"
    val defaultUsername = "John Doe"
    val defaultPhone = "0912345678"
    val defaultAddress = "123 Main Street"
    val defaultGender = "男"
    val defaultBirthday = "2000-01-01"

    // 記錄使用者更改的資料
    val uid = remember { mutableStateOf(defaultUid) }
    var uidError by remember { mutableStateOf(false) }

    val email = remember { mutableStateOf(defaultEmail) }
    var emailError by remember { mutableStateOf(false) }
    val emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$".toRegex()

    val username = remember { mutableStateOf(defaultUsername) }

    val phonenumber = remember { mutableStateOf(defaultPhone) }
    var phonenumberError by remember { mutableStateOf(false) }

    val address = remember { mutableStateOf(defaultAddress) }

    var inputGender by remember { mutableStateOf(defaultGender) }

    var inputYear by remember { mutableStateOf("") }
    var inputMonth by remember { mutableStateOf("") }
    var inputDay by remember { mutableStateOf("") }

    var password = remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    var checkpassword = remember { mutableStateOf("") }
    var checkpasswordVisible by remember { mutableStateOf(false) }

    // 顯示資料庫中的資料
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Text(
            text = "會員資料編輯",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Blue
        )

        // 用戶名稱欄位
        OutlinedTextField(
            value = uid.value,
            onValueChange = {
                uid.value = it
                uidError = it.isEmpty()
            },
            label = { Text(text = "請輸入用戶名稱") },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            isError = uidError,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            readOnly = uid.value != defaultUid // 如果資料庫中的資料不為預設值，則無法編輯
        )
        if (uidError) {
            Text(
                text = "用戶名稱為必填欄位",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        // 電子郵件欄位
        OutlinedTextField(
            value = email.value,
            onValueChange = {
                email.value = it
                emailError = !it.matches(emailRegex)
            },
            label = { Text(text = "請輸入信箱") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            shape = RoundedCornerShape(8.dp),
            isError = emailError,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            readOnly = email.value != defaultEmail // 顯示資料庫中資料
        )
        if (emailError) {
            Text(
                text = "請輸入有效的信箱",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        // 其他欄位的設置（同理處理其他欄位，如用戶名稱、生日、性別、手機號碼等）

        // 姓名欄位
        OutlinedTextField(
            value = username.value,
            onValueChange = { username.value = it },
            label = { Text(text = "請輸入姓名") },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        )

        // 手機號碼欄位
        OutlinedTextField(
            value = phonenumber.value,
            onValueChange = { phonenumber.value = it
                phonenumberError = it.isEmpty() || it.length != 10 },
            label = { Text(text = "請輸入手機號碼") },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            isError = phonenumberError,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
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

        // 地址欄位
        OutlinedTextField(
            value = address.value,
            onValueChange = { address.value = it },
            label = { Text(text = "請輸入地址") },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        )

        // 性別欄位 - 下拉選單
        var expanded by remember { mutableStateOf(false) }
        val genderOptions = listOf("男", "女", "其他")
        OutlinedTextField(
            value = inputGender,
            onValueChange = { },
            label = { Text(text = "選擇性別") },
            readOnly = true,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "選擇性別"
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .clickable { expanded = true }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            genderOptions.forEach { gender ->
                DropdownMenuItem(
                    text = { Text(text = gender) },
                    onClick = {
                        inputGender = gender
                        expanded = false
                    }
                )
            }
        }

        // 生日欄位 - 年、月、日
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextField(
                value = inputYear,
                onValueChange = { inputYear = it },
                label = { Text(text = "年") },
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = inputMonth,
                onValueChange = { inputMonth = it },
                label = { Text(text = "月") },
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = inputDay,
                onValueChange = { inputDay = it },
                label = { Text(text = "日") },
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
        }

        // 密碼欄位
        OutlinedTextField(
            value = password.value,
            onValueChange = {
                password.value = it
            },
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
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        )

        // 確認密碼欄位
        OutlinedTextField(
            value = checkpassword.value,
            onValueChange = { checkpassword.value = it },
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
            visualTransformation = if (checkpasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview21() {
    Edit(navController = rememberNavController())
}
