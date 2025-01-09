package com.example.potel.ui.petsfile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
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
    val petName by viewModel.petName.collectAsState()
    val petGender by viewModel.petGender.collectAsState()
    val contactInfo by viewModel.contactInfo.collectAsState()
    val petDiscribe by viewModel.petDiscribe.collectAsState()
    val petImage by viewModel.petImage.collectAsState()

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
                style = TextStyle(fontSize = 30.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold),
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
                label = "請輸入寵物性別",
                textState = petGender,
                onValueChange = { viewModel.updatePetGender(it) }
            )

            PetInfoTextField(
                label = "請輸飼主聯絡方式",
                textState = contactInfo,
                onValueChange = { viewModel.updateContactInfo(it) }
            )

            PetInfoTextField(
                label = "請輸入寵物描述",
                textState = petDiscribe,
                onValueChange = { viewModel.updatePetDiscribe(it) }
            )

            PetInfoTextField(
                label = "請上傳寵物影像",
                textState = petImage,
                onValueChange = { viewModel.updatePetImage(it) }
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
                    onClick = { navController.navigate(Screens.PetsFileDogs) },
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .padding(horizontal = 8.dp),
                ) {
                    Text(text = "Dog", color = Color.White)
                }

                // Cat 按鈕
                Button(
                    onClick = { navController.navigate(Screens.PetsFileDog) },
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

@Preview(showBackground = true)
@Composable
fun ScreenPetsFileAddPreview() {
    PotelTheme {
        ScreenPetsFileAdd(navController = rememberNavController())
    }
}