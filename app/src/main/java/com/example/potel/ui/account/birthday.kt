package com.example.potel.ui.account


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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun birthday(navController: NavHostController) {


    val birthday = remember { mutableStateOf("") }

    var inputText by remember { mutableStateOf("") }

    val yearRange = (1960..2024).map { it.toString() }
    var selectedYear by remember { mutableStateOf("") }
    val filteredYears = yearRange.filter { it.startsWith(inputText, true) }

    val monthRange = (1..12).map { it.toString() }
    var selectedMonth by remember { mutableStateOf("") }
    val filteredMonths = monthRange.filter { it.startsWith(inputText, true) }

    val dayRange = (1..31).map { it.toString() }
    var selectedDay by remember { mutableStateOf("") }
    val filteredDays = dayRange.filter { it.startsWith(inputText, true) }


    var selectedText by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
//    expanded = expanded && filteredbirthday.isNotEmpty()

    val gender = remember { mutableStateOf("") }




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
            value = uid.value,
            onValueChange = { uid.value = it },
            label = { Text(text = "請輸入用戶名稱") },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )

        TextField(
            value = birthday.value,
            onValueChange = { birthday.value = it },
            label = { Text(text = "請選擇生日") },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
        )
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it })
        {
            TextField(
                // 設為true則無法輸入
                readOnly = false,
                // 正確設定TextField與ExposedDropdownMenu對應位置。enabled為true方可展開下拉選單
                modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryEditable, true),
                value = inputText,
                // 當使用者修改文字值時呼叫
                onValueChange = {
                    inputText = it
                    // 值改變時會彈出選項
                    expanded = true
                },
                singleLine = true,
                label = { Text("T-shirt Size") },
                // 末端顯示下箭頭圖示
                trailingIcon = { TrailingIcon(expanded = expanded) }
            )
            ExposedDropdownMenu(
                // 設定是否彈出下拉選單
                expanded = expanded,
                // 點擊下拉選單外部時
                onDismissRequest = { expanded = false }
            ) {
                // 下拉選單內容由DropdownMenuItem項目組成
                filteredOptions.forEach { yearRange ->
                    DropdownMenuItem(
                        text = { Text(yearRange) },
                        // 點選項目後呼叫
                        onClick = {
                            // 點選項目的文字成爲被選取項目與輸入方塊的文字
                            selectedText = yearRange
                            inputText = yearRange
                            // 將狀態設定為收回下拉選單
                            expanded = false
                        }
                    )
                }
            }


            OutlinedTextField(
                value = gender.value,
                onValueChange = { gender.value = it },
                label = { Text(text = "請選擇性別") },
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )

            OutlinedTextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = { Text(text = "請輸入信箱") },
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )

            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
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
}
@Preview(showBackground = true)
@Composable
    fun DefaultPreview9() {
        birthday(navController = rememberNavController())
    }
