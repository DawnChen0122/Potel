package com.example.potel.ui.forumZone

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

@Composable
fun PostAddScreen(
    navController: NavHostController, // 用於導航的控制器
) {
    // 從導航堆疊中取得 ForumVM 這個 ViewModel
    val backStackEntry = navController.getBackStackEntry(ForumScreens.ForumScreen.name)
    val forumVM: ForumVM = viewModel(backStackEntry)

    val memberId = 1 // 使用固定的 memberId，未來可以根據用戶登入情況動態改變

    // 管理 UI 狀態，保存標題、內容和選擇的圖片
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    // 協程範圍，方便處理異步操作
    val scope = rememberCoroutineScope()
    // Snackbar 用於顯示提示訊息
    val snackbarHostState = remember { SnackbarHostState() }
    // 用於獲取 Android 上下文
    val context = LocalContext.current

    // 用於選擇圖片的功能
    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(), // 僅選擇圖片
        onResult = { uri: Uri? -> selectedImageUri = uri } // 設定選擇的圖片 URI
    )

    // 主要的界面容器
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 44.dp, start = 16.dp, end = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // 標題輸入框
                    OutlinedTextField(
                        value = title,
                        onValueChange = { if (it.length <= 200) title = it }, // 限制標題長度
                        label = { Text("標題") }, // 顯示標籤
                        modifier = Modifier.width(350.dp) // 設置寬度
                    )
                    Spacer(modifier = Modifier.height(3.dp)) // 標題和內容之間的間隔

                    // 內容輸入框
                    OutlinedTextField(
                        value = content,
                        onValueChange = { if (it.length <= 1000) content = it }, // 限制內容長度
                        label = { Text("內容") }, // 顯示標籤
                        modifier = Modifier
                            .width(350.dp)
                            .height(150.dp), // 設置高度
                        minLines = 1 // 設置最小行數
                    )
                    Spacer(modifier = Modifier.height(10.dp)) // 內容和圖片之間的間隔

                    // 圖片選擇區域
                    Column(
                        Modifier
                            .size(350.dp)
                            .border(1.dp, Color.DarkGray, RoundedCornerShape(5.dp)) // 圖片選擇框的邊框
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            // 顯示已選擇的圖片
                            selectedImageUri?.let { uri ->
                                AsyncImage(
                                    model = uri,
                                    contentDescription = "Selected Image",
                                    contentScale = ContentScale.Crop // 縮放圖片以符合框框
                                )
                            }

                            // 上傳照片按鈕
                            Button(
                                onClick = {
                                    pickImageLauncher.launch(
                                        PickVisualMediaRequest(
                                            ActivityResultContracts.PickVisualMedia.ImageOnly // 僅選擇圖片
                                        )
                                    )
                                },
                                modifier = Modifier.align(Alignment.Center) // 按鈕放在中間
                            ) {
                                Text(if (selectedImageUri != null) "更改照片" else "上傳照片") // 根據是否選擇了圖片改變按鈕文字
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp)) // 圖片選擇區域和發布按鈕之間的間隔

                    // 發布貼文按鈕
                    Button(
                        onClick = {
                            if (title.isEmpty() || content.isEmpty()) {
                                // 如果標題或內容為空，顯示錯誤提示
                                scope.launch { snackbarHostState.showSnackbar("標題和內容都必須填寫！") }
                            } else {
                                // 處理圖片為 MultipartBody.Part 格式
                                val imagePart = selectedImageUri?.let { uri ->
                                    val byteArray = context.contentResolver.openInputStream(uri)?.readBytes()
                                    byteArray?.let {
                                        val imageRequestBody = it.toRequestBody("image/*".toMediaTypeOrNull()) // 將圖片轉換為 RequestBody
                                        MultipartBody.Part.createFormData("image", "image.jpg", imageRequestBody) // 創建 Multipart 表單數據
                                    }
                                }
                                // 構建新的文章資料
                                val newPost = NewPost(
                                    memberId = memberId,
                                    title = title,
                                    content = content
                                )
                                // 呼叫 ViewModel 的 addPost 方法來提交資料
                                forumVM.addPost(newPost, imagePart)
                            }
                        }
                    ) {
                        Text("發布貼文") // 按鈕文字
                    }
                }
            }
        }

        // 顯示 Snackbar 提示
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

