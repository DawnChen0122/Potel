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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.potel.R
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

@OptIn(ExperimentalMaterial3Api::class)
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
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    // 用於選擇圖片的功能
    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri: Uri? -> selectedImageUri = uri }
    )

    // 主界面容器
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button)
                        )
                    }
                },
                actions = {
                    TextButton (onClick = {
                        if (title.isEmpty() || content.isEmpty()) {
                            // 顯示錯誤提示
                            scope.launch { snackbarHostState.showSnackbar("標題和內容都必須填寫！") }
                        } else {
                            // 處理圖片為 MultipartBody.Part 格式
                            val imagePart = selectedImageUri?.let { uri ->
                                val byteArray = context.contentResolver.openInputStream(uri)?.readBytes()
                                byteArray?.let {
                                    val imageRequestBody = it.toRequestBody("image/*".toMediaTypeOrNull())
                                    MultipartBody.Part.createFormData("image", "image.jpg", imageRequestBody)
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
                            navController.popBackStack()
                        }
                    }) {
                        Text("發布貼文")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        PostAddContent(
            title = title,
            onTitleChange = { if (it.length <= 200) title = it },
            content = content,
            onContentChange = { if (it.length <= 1000) content = it },
            selectedImageUri = selectedImageUri,
            onSelectImage = { pickImageLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun PostAddContent(
    title: String,
    onTitleChange: (String) -> Unit,
    content: String,
    onContentChange: (String) -> Unit,
    selectedImageUri: Uri?,
    onSelectImage: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = onTitleChange,
                    label = { Text("標題") },
                    modifier = Modifier.width(350.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = content,
                    onValueChange = onContentChange,
                    label = { Text("內容") },
                    modifier = Modifier
                        .width(350.dp)
                        .height(150.dp),
                    minLines = 1
                )
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    Modifier
                        .size(350.dp)
                        .border(1.dp, Color.DarkGray, RoundedCornerShape(5.dp))
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        selectedImageUri?.let { uri ->
                            AsyncImage(
                                model = uri,
                                contentDescription = "Selected Image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                        Button(
                            onClick = onSelectImage,
                            modifier = Modifier.align(Alignment.Center)
                        ) {
                            Text(if (selectedImageUri != null) "更改照片" else "上傳照片")
                        }
                    }
                }
            }
        }
    }
}



