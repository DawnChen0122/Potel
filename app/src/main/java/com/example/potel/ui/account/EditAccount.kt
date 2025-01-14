package com.example.potel.ui.account

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch


@Composable
fun Edit(
    viewModel: EditViewModel = viewModel(
        factory = EditViewModelFactory(LocalContext.current.getSharedPreferences("member", Context.MODE_PRIVATE))
    ),
    navController: NavHostController
) {
    val member by viewModel.member.collectAsState()  // 觀察 member 資料並且自動更新 UI
    val scrollState = rememberScrollState()

    LaunchedEffect(member) {
        viewModel.loadMember()
        Log.d("EditScreen", "Member updated: $member")
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(2.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .verticalScroll(scrollState)
            .border(width = 5.dp, color = Color(0xFF000000))
            .padding(5.dp)
            .fillMaxSize()
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = Color(0xFFF7E3A6))
            .padding(12.dp)
    )
    {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .padding(10.dp)
        ) {
            Text(
                modifier = Modifier,
                text = "編輯會員資料",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFFD700)
            )


            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.padding(10.dp)
            ) {

                // 顯示並更新姓名欄位（只顯示）
                Text(text = "姓名: ${member.name}", style = MaterialTheme.typography.bodyLarge)

                // 顯示性別（只顯示）
                Text(text = "性別: ${member.gender}", style = MaterialTheme.typography.bodyLarge)

                // 顯示生日（只顯示）
                Text(text = "生日: ${member.birthday}", style = MaterialTheme.typography.bodyLarge)

                // 顯示並更新手機號碼欄位（可編輯）
                OutlinedTextField(
                    value = member.cellphone,
                    onValueChange = { viewModel.updateCellphone(it) },
                    label = { Text(text = "請輸入手機號碼") },
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                )

                // 顯示並更新 email 欄位（可編輯）
                OutlinedTextField(
                    value = member.email,
                    onValueChange = { viewModel.updateEmail(it) },
                    label = { Text(text = "請輸入電子郵件") },
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                )

                // 顯示並更新密碼欄位（可編輯）
                OutlinedTextField(
                    value = member.passwd,
                    onValueChange = { viewModel.updatePassword(it) },
                    label = { Text(text = "請輸入密碼") },
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    visualTransformation = PasswordVisualTransformation()  // 密碼加密顯示
                )

                // 顯示並更新地址欄位（可編輯）
                OutlinedTextField(
                    value = member.address,
                    onValueChange = { viewModel.updateAddress(it) },
                    label = { Text(text = "請輸入地址") },
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                )


                Button(
                    onClick = {
                        if (member.email.isEmpty() || member.passwd.isEmpty() || member.address.isEmpty()
                            || member.cellphone.isEmpty()
                        ) {
                            val updatedMember = Edit(
                                passwd = member.passwd,
                                cellphone = member.cellphone,
                                address = member.address,
                                email = member.email
                            )
                            viewModel.viewModelScope.launch {
                                viewModel.edit(updatedMember)
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp),
                    shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFFA500),)
                ) {
                    Text(text = "更改資料",
                        fontSize = 40.sp,
                        color = Color.White)
                }
            }
        }

    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview666() {
    Edit(navController = rememberNavController())
}
