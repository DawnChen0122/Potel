package com.example.potel.ui.forumZone

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    val forumVM: ForumVM = viewModel(navController.getBackStackEntry(ForumScreens.ForumScreen.name))

    val memberId = 5 // 使用固定的 memberId，未來可以根據用戶登入情況動態改變
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

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
                    IconButton(onClick = {
                        if (title.isNotEmpty() || content.isNotEmpty()) showDialog = true
                        else navController.navigateUp()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button),
                            tint = Color.LightGray
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = colorResource(R.color.forum)
                ),
                actions = {
                    Button(onClick = {
                        if (title.isEmpty() || content.isEmpty()) {
                            scope.launch { snackbarHostState.showSnackbar("標題和內容都必須填寫！") }
                        } else {
                            val imagePart = selectedImageUri?.let { uri ->
                                context.contentResolver.openInputStream(uri)?.readBytes()?.let {
                                    val imageRequestBody = it.toRequestBody("image/*".toMediaTypeOrNull())
                                    MultipartBody.Part.createFormData("image", "image.jpg", imageRequestBody)
                                }
                            }
                            val newPost = NewPost(memberId = memberId, title = title, content = content)
                            forumVM.addPost(newPost, imagePart)
                            navController.popBackStack()
                        }
                    }, shape = RoundedCornerShape(8.dp), colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.foruButton),
                        contentColor = Color.Black
                    )) {
                        Text("發布貼文", fontSize = 15.sp)
                    }
                    Spacer(Modifier.width(8.dp))
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { snackbarData ->
                Snackbar(
                    snackbarData = snackbarData,
                    containerColor = Color.White,
                    contentColor = Color.Black
                )
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            PostAddContent(
                title = title,
                onTitleChange = { if (it.length <= 200) title = it },
                content = content,
                onContentChange = { if (it.length <= 1000) content = it },
                selectedImageUri = selectedImageUri,
                onSelectImage = { pickImageLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) }
            )
        }
    }

    if (showDialog) {
        BasicAlertDialog(
            onDismissRequest = { showDialog = false },
            content = {
                Column(
                    modifier = Modifier
                        .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
                        .height(240.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.height(50.dp))
                    Text("捨棄發布")
                    Text("確定不發布此貼文？")
                    Spacer(Modifier.height(50.dp))
                    Row(
                        modifier = Modifier.height(50.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = { showDialog = false },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = Color.Black
                            ),
                            border = BorderStroke(1.dp, Color.DarkGray),
                            modifier = Modifier.width(110.dp)
                        ) {
                            Text("取消")
                        }
                        Spacer(Modifier.width(20.dp))
                        Button(
                            onClick = {
                                showDialog = false
                                navController.navigate(ForumScreens.ForumScreen.name)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = Color.Red
                            ),
                            border = BorderStroke(1.dp, Color.DarkGray),
                            modifier = Modifier.width(110.dp)
                        ) {
                            Text("捨棄發布")
                        }
                    }
                }
            }
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
    LazyColumn(modifier = modifier.fillMaxSize().background(colorResource(R.color.forum))) {
        item {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HorizontalDivider(
                    Modifier.padding(start = 10.dp, end = 10.dp),
                    thickness = 1.dp,
                    color = Color.DarkGray
                )
                Spacer(Modifier.height(35.dp))
                OutlinedTextField(
                    value = title,
                    onValueChange = onTitleChange,
                    label = { Text("標題", color = Color.LightGray) },
                    modifier = Modifier.width(350.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.LightGray,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.LightGray,
                        cursorColor = colorResource(R.color.foruButton)
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = content,
                    onValueChange = onContentChange,
                    label = { Text("內容", color = Color.LightGray)},
                    modifier = Modifier
                        .width(350.dp)
                        .height(150.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.LightGray,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.LightGray,
                        cursorColor = colorResource(R.color.foruButton)
                    ),
                    minLines = 1
                )
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    Modifier
                        .size(350.dp)
                        .border(1.dp, Color.LightGray, RoundedCornerShape(4.dp))
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
                            modifier = Modifier.align(Alignment.Center),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.LightGray,
                                contentColor = Color.Black
                            )
                        ) {
                            Text(if (selectedImageUri != null) "更改照片" else "上傳照片")
                        }
                    }
                }
            }
        }
    }
}



