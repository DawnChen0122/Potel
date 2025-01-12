package com.example.potel.ui.petsfile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.potel.ui.theme.PotelTheme

//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            PetInfoForm()
//        }
//    }
//}

@Composable
fun ScreenPetsFileAdd(
    viewModel: PetFileAddViewModel = viewModel(),
    navController: NavHostController
) {
    val ownerName by viewModel.ownerName.collectAsState()
    val petId by viewModel.petId.collectAsState()
    val petName by viewModel.petName.collectAsState()
    val petBreed by viewModel.petBreed.collectAsState()
    val petGender by viewModel.petGender.collectAsState()
    val petImages by viewModel.petImages.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFFF0E68C))
    ) {
        // 上方的內容
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 60.dp), // 留出空間給底部按鈕
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(45.dp)
        ) {
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "Pet Information",
                style = TextStyle(
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = Color.Black
            )

            // 使用 ViewModel 中的函式來更新狀態
            PetInfoTextField(
                label = "請輸入飼主名稱",
                textState = ownerName,
                onValueChange = { viewModel.updateOwnerName(it) }
            )

            PetInfoTextField(
                label = "請輸入寵物名稱",
                textState = petName,
                onValueChange = { viewModel.updatePetName(it) }
            )

            PetInfoTextField(
                label = "請輸入寵物品種",
                textState = petBreed,
                onValueChange = { viewModel.updatePetBreed(it) }
            )


            Row {
                val isMale = petGender == "Male"
                Text(
                    modifier = Modifier

                        .padding(horizontal = 12.dp)
                        .weight(1f)
                        .border(
                            if (isMale) 3.dp else 0.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .background(Color.Blue, shape = RoundedCornerShape(12.dp))
                        .padding(vertical = 12.dp)
                        .clickable(onClick = viewModel::onMaleClick),
                    text = "公",
                    textAlign = TextAlign.Center
                )
                val isFemale = petGender == "Female"
                Text(
                    modifier = Modifier

                        .padding(horizontal = 12.dp)
                        .weight(1f)
                        .border(
                            if (isFemale) 3.dp else 0.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .background(Color.Red, shape = RoundedCornerShape(12.dp))
                        .padding(vertical = 12.dp)
                        .clickable(onClick = viewModel::onFemaleClick),
                    text = "母",
                    textAlign = TextAlign.Center
                )

            }

            // petImages 改為數字輸入
            PetInfoNumberField(
                label = "請上傳寵物圖片",
                numberState = petImages,
                onValueChange = { viewModel.updatePetImages(it) }
            )
        }

        // 底部的 SKIP 按鈕
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter) // 置中對齊
                .padding(bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp) // 兩個按鈕之間的間隔
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                // Dog 按鈕
                Button(
                    onClick = {
                        viewModel.onAddDogClick() // 提交資料
                        navController.navigate(Screens.PetsFileDogs.name)
                    },
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .padding(horizontal = 8.dp),
                ) {
                    Text(text = "Dog", color = Color.White)
                }

                // Cat 按鈕
                Button(
                    onClick = {
                        viewModel.onAddCatClick() // 提交資料
                        navController.navigate(Screens.PetsFileCats.name)
                    },
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .padding(horizontal = 8.dp),
                ) {
                    Text(text = "Cat", color = Color.White)
                }
            }
        }
    }
}

// 用於一般文字輸入的 TextField
@Composable
fun PetInfoTextField(label: String, textState: String, onValueChange: (String) -> Unit) {
    TextField(
        value = textState,
        onValueChange = { onValueChange(it) },
        label = { Text(text = label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp) // 左右邊距，確保不貼邊
            .clip(RoundedCornerShape(12.dp)) // 圓角輸入框
            .background(Color.White), // 輸入框背景顏色
        singleLine = true
    )
}

// 用於數字輸入的 TextField
@Composable
fun PetInfoNumberField(label: String, numberState: Int, onValueChange: (Int) -> Unit) {
    TextField(
        value = numberState.toString(),
        onValueChange = { newValue ->
            // 確保是數字才能更新
            newValue.toIntOrNull()?.let {
                onValueChange(it)
            }
        },
        label = { Text(text = label) },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp) // 左右邊距，確保不貼邊
            .clip(RoundedCornerShape(12.dp)) // 圓角輸入框
            .background(Color.White), // 輸入框背景顏色
        singleLine = true
    )
}


@Preview(showBackground = true)
@Composable

fun ScreenPetsFileAddPreview() {
    PotelTheme {
        ScreenPetsFileAdd(navController = rememberNavController())
    }
}
