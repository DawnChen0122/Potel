package com.example.potel.ui.account


import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
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
import com.example.potel.ui.booking.BookingScreens
import com.example.potel.ui.home.AccountScreens
import com.example.potel.ui.theme.TipColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun Signup(
    viewModel: AccountViewModel = viewModel(),
    navController: NavHostController
) {

    val name by viewModel.name.collectAsState()

    val email by viewModel.email.collectAsState()

    val birthday by viewModel.birthday.collectAsState()

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

    val gender by viewModel.gender.collectAsState()


    val passwd by viewModel.passwd.collectAsState()
    var passwdVisible by remember { mutableStateOf(false) }

    val checkpasswd by viewModel.checkpasswd.collectAsState()
    var checkpasswdVisible by remember { mutableStateOf(false) }


    val cellphone by viewModel.cellphone.collectAsState()

    val address by viewModel.address.collectAsState()

    val scrollState = rememberScrollState()

    var errorMessage by remember { mutableStateOf<String?>(null) }

    val focusedColors = TipColor.deep_brown


    Column(
        verticalArrangement = Arrangement.spacedBy(2.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .verticalScroll(scrollState)
//            .border(width = 5.dp, color = Color(0xFF000000))
//            .padding(5.dp)
            .fillMaxSize()
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = TipColor.light_brown)
            .padding(12.dp)
    ) {
        Text(
            text = "會員註冊",
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFAA8066)
        )



        OutlinedTextField(
            value = name,
            onValueChange = viewModel::namechange,
            label = { Text(
                text = "請輸入姓名",
                color = TipColor.deep_brown) },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)

        )


        OutlinedTextField(
            value = email,
            onValueChange = viewModel::emailchange,
            label = { Text(text ="請輸入信箱" ,
                    color = TipColor.deep_brown) },
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

        ) {
            Text(
                text = "請選擇出生年月日",
                modifier = Modifier.padding(10.dp)
                ,color = TipColor.deep_brown,
            )
        }


        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.Top
        )
        {

            LaunchedEffect(inputYear, inputMonth, inputDay) {
                viewModel.birthdaychange(inputYear, inputMonth, inputDay)
            }

            ExposedDropdownMenuBox(
                expanded = expandedYear,
                onExpandedChange = { expandedYear = it },
                modifier = Modifier.weight(1f)
            ) {
                TextField(
                    readOnly = true,
                    modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryEditable, true),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = focusedColors,
                        unfocusedIndicatorColor = focusedColors,
                        focusedLabelColor = focusedColors,
                        unfocusedLabelColor = focusedColors
                    ),
                    value = inputYear,
                    onValueChange = {
                        inputYear = it
                        expandedYear = true
                    },
                    singleLine = true,
                    label =
                    {Text(text ="年" ,
                        color = TipColor.deep_brown)},
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
                    label =
                    {Text(text ="月" ,
                        color = TipColor.deep_brown)},
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
                    label = {Text(text ="日" ,
                        color = TipColor.deep_brown)},
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
        )
        {
            Text(
                text = "請選擇性別",
                modifier = Modifier.padding(10.dp) ,
                color = TipColor.deep_brown,
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
                label =
                {Text(text ="性別" ,
                    color = TipColor.deep_brown)},
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
                            viewModel.genderchange(genderOption)
                        }
                    )
                }
            }
        }


        OutlinedTextField(
            value = passwd,
            onValueChange = viewModel::passwdchange,
            label = {Text(text ="密碼" ,
                color = TipColor.deep_brown)},
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "密碼"
                )
            },
            trailingIcon = {
                Text(
                    text = if (passwdVisible) "隱藏" else "顯示",
                    modifier = Modifier.clickable {
                        passwdVisible = !passwdVisible
                    }
                    ,color = TipColor.deep_brown,
                )
            },
            isError = viewModel.passwdError,
            shape = RoundedCornerShape(8.dp),
            visualTransformation = if (passwdVisible) VisualTransformation.None else PasswordVisualTransformation(),
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
        if (viewModel.passwdError) {
            Text(
                text = "密碼需在6至20字符內，且包含字母和數字",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp)
            )
        }


        OutlinedTextField(
            value = checkpasswd,
            onValueChange = viewModel::checkpasswdchange,
            label = {Text(text ="再次確認密碼" ,
                color = TipColor.deep_brown)},
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "再次確認密碼"
                )
            },
            trailingIcon = {
                Text(
                    text = if (checkpasswdVisible) "隱藏" else "顯示",
                    modifier = Modifier.clickable {
                        checkpasswdVisible = !checkpasswdVisible
                    }
                )
            },
            isError = viewModel.checkpasswdError,
            shape = RoundedCornerShape(8.dp),
            visualTransformation = if (checkpasswdVisible) VisualTransformation.None else PasswordVisualTransformation(),
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
        if (viewModel.checkpasswdError) {
            Text(
                text = "密碼需輸入相同",
                color = Color.Red,
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 16.dp)
            )
        }



        OutlinedTextField(
            value = cellphone,
            onValueChange = viewModel::cellphonechange,
            label = {Text(text ="請輸入手機號碼" ,
                    color = TipColor.deep_brown)},
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            isError = viewModel.cellphoneError,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
        if (viewModel.cellphoneError) {
            Text(
                text = "手機號碼為十位數字",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp)
            )
        }


        OutlinedTextField(
            value = address,
            onValueChange = viewModel::addresschange,
            label = {Text(text ="請輸入地址" ,
                color = TipColor.deep_brown)
                    },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )


        errorMessage?.let {
            Text(
                text = it,
                color = Color.Red,
                fontSize = 20.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Button(
            onClick = {

                if (email.isEmpty() || passwd.isEmpty() || checkpasswd.isEmpty()
                    || name.isEmpty() || cellphone.isEmpty() || address.isEmpty()
                ) {
                    errorMessage = "欄位不得空白"
                } else {
                    viewModel.viewModelScope.launch {
                        val member = Member(
                            name = name,
                            email = email,
                            gender = gender.toString(),
                            passwd = passwd,
                            cellphone = cellphone,
                            address = address,
                            birthday = birthday
                        )
                        val result = viewModel.addmember(member)

                        if (result.message  == "Account added successfully" ) {

                            errorMessage = "註冊成功"
                            Log.d("Registration", "註冊成功，準備跳轉到登入頁面")
                            delay(5000)
                            navController.navigate(AccountScreens.Login.name)
                        } else {
                            errorMessage =  "註冊失敗"
                            Log.e("Registration", "註冊失敗，錯誤訊息: ${result.message}")
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFDBC8B6),
            )
        ) {
            Text(
                text = "註冊",
                fontSize = 50.sp,
                color = TipColor.deep_brown,
                )
        }

    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview1() {
    Signup(navController = rememberNavController())
}
