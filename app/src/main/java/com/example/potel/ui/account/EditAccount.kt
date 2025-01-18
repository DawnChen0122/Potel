package com.example.potel.ui.account

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.potel.ui.home.AccountScreens
import com.example.potel.ui.petsfile.PetsFileScreens
import com.example.potel.ui.theme.TipColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun Edit(
    viewModel: EditViewModel = viewModel(
        factory = EditViewModelFactory(
            preferences = LocalContext.current.getSharedPreferences("member", Context.MODE_PRIVATE),
            apiService = RetrofitInstance.api
        ),
    ),
    navController: NavHostController
) {
    val member by viewModel.member.collectAsState()

    var errorMessage by remember { mutableStateOf<String?>(null) }

    val genderMap = mapOf(
        "M" to "男",
        "F" to "女",
        "N" to "不願透露"
    )


    LaunchedEffect(member) {
        try {
            viewModel.loadMember()
            Log.d("EditScreen", "Member loaded: $member")
        } catch (e: Exception) {
            Log.e("EditScreen", "Error loading member data", e)
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(2.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
//            .border(width = 5.dp, color = Color(0xFF000000))
//            .padding(5.dp)
            .fillMaxSize()
            .background(color = TipColor.light_brown)
            .padding(12.dp)
            .background(color = TipColor.light_brown)
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
                color = Color(0xFFAA8066)
            )


            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.padding(10.dp)
            ) {

                Text(text = "姓名: ${member.name}"
                       ,color = TipColor.deep_brown, style = MaterialTheme.typography.bodyLarge)

                Spacer(modifier = Modifier.height(16.dp))

                val genderText = genderMap[member.gender] ?: "未知" // Default to "未知" if not found
                Text(text = "性別: $genderText",
//                Text(text = "性別: ${member.gender}"
                    color = TipColor.deep_brown, style = MaterialTheme.typography.bodyLarge)

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "生日: ${member.birthday}"
                    ,color = TipColor.deep_brown, style = MaterialTheme.typography.bodyLarge)

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = member.cellphone,
                    onValueChange = { viewModel.updateCellphone(it) },
                    label = { Text(
                        text = "請輸入手機號碼",
                        color = TipColor.deep_brown) },
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                )


                OutlinedTextField(
                    value = member.email,
                    onValueChange = { viewModel.updateEmail(it) },
                    label = { Text(
                        text = "請輸入電子郵件",
                        color = TipColor.deep_brown) },
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                )


                OutlinedTextField(
                    value = member.passwd,
                    onValueChange = { viewModel.updatePassword(it) },
                    label = { Text(
                        text = "請輸入密碼",
                        color = TipColor.deep_brown) },
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    visualTransformation = PasswordVisualTransformation()
                )


                OutlinedTextField(
                    value = member.address,
                    onValueChange = { viewModel.updateAddress(it) },
                    label = { Text(
                        text = "請輸入地址",
                        color = TipColor.deep_brown) },
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                )



                errorMessage?.let {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = it,
                        color = Color.Red,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }


                Button(
                    onClick = {
                        Log.d("EditButton", "Button clicked, checking fields: email = ${member.email}, passwd = ${member.passwd}, cellphone = ${member.cellphone}, address = ${member.address}")

                        if (member.email.isEmpty() || member.passwd.isEmpty()
                            || member.cellphone.isEmpty() || member.address.isEmpty()
                        ) {
                            errorMessage = "欄位不得空白"
                            Log.d("EditButton", "Fields are empty, errorMessage set to: $errorMessage")

                        } else if (member.email.isNotEmpty() && member.passwd.isNotEmpty() && member.address.isNotEmpty()
                            && member.cellphone.isNotEmpty()
                        ) {
                            val updatedMember = Edit1(
                                passwd = member.passwd,
                                cellphone = member.cellphone,
                                address = member.address,
                                email = member.email,
                                memberid = member.memberid
                            )
                            viewModel.viewModelScope.launch {
                                try {
                                    Log.d("EditButton", "Updating member data: $updatedMember")
                                    val result = viewModel.edit(updatedMember)
                                    Log.d("EditButton", "更新成功: $result")
                                    errorMessage = "更新成功"
                                    delay(1000)
                                    navController.navigate(PetsFileScreens.PetsFileFirst.name)
                                } catch (e: Exception) {
                                    Log.e("EditButton", "更新失敗: ${e.message}")
                                    errorMessage = "更新失敗"
                                }
                            }
                        } else {
                            Log.d("EditButton", "請填寫所有欄位")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFDBC8B6),
                    )
                ) {
                    Text(
                        text = "更改資料",
                        fontSize = 40.sp,
                        color = TipColor.deep_brown,
                    )
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
