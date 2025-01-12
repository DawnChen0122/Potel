package com.example.potel.ui.account

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


@Composable
fun Edit(
    viewModel: EditViewModel = viewModel(
        factory = EditViewModelFactory(LocalContext.current.getSharedPreferences("member", Context.MODE_PRIVATE))
    ),
    navController: NavHostController
) {
    val member by viewModel.member.collectAsState()  // 觀察 member 資料並且自動更新 UI
    val scrollState = rememberScrollState()

    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .verticalScroll(scrollState)
            .fillMaxSize()
            .padding(16.dp)
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
                color = Color.Blue
            )



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
    }
}


}
@Preview(showBackground = true)
@Composable
fun DefaultPreview666() {
    Edit(navController = rememberNavController())
}
