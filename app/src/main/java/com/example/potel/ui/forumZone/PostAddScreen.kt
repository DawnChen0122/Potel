package com.example.potel.ui.forumZone

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage

@Composable
fun PostAddScreen(
    navController: NavHostController,
) {
    val backStackEntry = navController.getBackStackEntry(ForumScreens.ForumScreen.name)
    val forumVM: ForumVM = viewModel(backStackEntry)

    val memberId = 1 //fix me

    // 管理UI輸入的狀態
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri: Uri? ->
            selectedImageUri = uri // 存儲選擇的圖片 URI
        }
    )

    LazyColumn(
        Modifier.fillMaxSize()
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 64.dp, start = 16.dp, end = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 標題輸入框
                OutlinedTextField(
                    value = title,
                    onValueChange = {
                        if (it.length <= 200) title = it
                    },
                    label = { Text(text = "標題") },
                    isError = title.isEmpty() && errorMessage.isNotEmpty(),
                    modifier = Modifier
                        .width(270.dp)
                        .padding(bottom = 16.dp),
                )

                // 內容輸入框
                OutlinedTextField(
                    value = content,
                    onValueChange = {
                        if (it.length <= 1000) content = it
                    },
                    label = { Text(text = "內容") },
                    isError = content.isEmpty() && errorMessage.isNotEmpty(),
                    modifier = Modifier
                        .width(270.dp)
                        .height(200.dp),
                    minLines = 1
                )

                // 顯示錯誤訊息
                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                // 圖片選擇按鈕
                Spacer(modifier = Modifier.height(16.dp))
                TextButton(onClick = {
                    pickImageLauncher.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                }) {
                    Text(text = "上傳照片")
                }

                // 顯示選擇的圖片
                selectedImageUri?.let { uri ->
                    AsyncImage(
                        model = uri,
                        contentDescription = "Selected Image",
                        modifier = Modifier
                            .height(200.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (title.isEmpty() || content.isEmpty()) {
                            errorMessage = "標題和內容都必須填寫！"
                        } else {
                            errorMessage = ""  // 清除錯誤訊息
                            val newPost = Post(
                                memberId = memberId,
                                title = title,
                                content = content,
                                postImage = selectedImageUri?.toString() // 儲存圖片 URI 為字串
                            )
                            forumVM.addPost(newPost) // 呼叫 ViewModel 方法
                            navController.popBackStack() // 返回上一頁
                        }
                    }
                ) {
                    Text(text = "發布貼文")
                }
            }
        }
    }
}
