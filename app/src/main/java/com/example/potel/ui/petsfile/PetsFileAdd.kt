package com.example.potel.ui.petsfile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import coil.compose.AsyncImage
import com.example.potel.ui.forumZone.ForumScreens
import com.example.potel.ui.forumZone.ForumVM
import com.example.potel.ui.forumZone.NewPost
import com.example.potel.ui.theme.PotelTheme
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

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
    navController: NavHostController,
) {
    val context = LocalContext.current
    val ownerName by viewModel.ownerName.collectAsState()
    val petName by viewModel.petName.collectAsState()
    val petBreed by viewModel.petBreed.collectAsState()
    val petGender by viewModel.petGender.collectAsState()
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    // 用於選擇圖片的功能
    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri: Uri? -> selectedImageUri = uri }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFFF0E68C))
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
                .padding(bottom = 60.dp),
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
                        .background(Color(0xFFADD8E6), shape = RoundedCornerShape(12.dp))
                        .padding(vertical = 12.dp)
                        .clickable(onClick = viewModel::onMaleClick),
                    text = "Male",
                    textAlign = TextAlign.Center,

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
                        .background(Color(0xFFFFB6C1), shape = RoundedCornerShape(12.dp))
                        .padding(vertical = 12.dp)
                        .clickable(onClick = viewModel::onFemaleClick),
                    text = "Female",
                    textAlign = TextAlign.Center

                )
            }

            Box(modifier = Modifier.fillMaxSize()) {
                selectedImageUri?.let { uri ->
                    AsyncImage(
                        model = uri,
                        contentDescription = "Selected Image",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(horizontal = 12.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .border(2.dp, Color.Gray, RoundedCornerShape(8.dp))
                    )
                }

                Button(
                    onClick = {
                        pickImageLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    },
                    modifier = Modifier.align(Alignment.Center),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.LightGray,
                        contentColor = Color.Black
                    )
                ) {
                    Text(if (selectedImageUri != null) "更改照片" else "上傳照片")
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
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
                            // 準備圖片和其他資料
                            val imagePart = selectedImageUri?.let { uri ->
                                context.contentResolver.openInputStream(uri)?.readBytes()?.let {
                                    val imageRequestBody = it.toRequestBody("image/*".toMediaTypeOrNull())
                                    MultipartBody.Part.createFormData("image", "image.jpg", imageRequestBody)
                                }
                            }

                            viewModel.onAddDogClick(imagePart = imagePart) // 提交資料
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
}


@Composable
fun PetInfoTextField(label: String, textState: String, onValueChange: (String) -> Unit) {
    TextField(
        value = textState,
        onValueChange = { onValueChange(it) },
        label = { Text(text = label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White),
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
