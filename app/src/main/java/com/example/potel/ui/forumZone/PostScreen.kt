package com.example.potel.ui.forumZone

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Share
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
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.potel.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreen(navController: NavHostController) {

    val backStackEntry = navController.getBackStackEntry(ForumScreens.ForumScreen.name)
    val forumVM: ForumVM = viewModel(backStackEntry)
    val postDetail = forumVM.postSelectedState.collectAsState()
    val comments by forumVM.postSelectedCommentsList.collectAsState()

    val scope = rememberCoroutineScope()
    val hostState = remember { SnackbarHostState() }

    val context = LocalContext.current
    val preferences = context.getSharedPreferences("member", Context.MODE_PRIVATE)
    val memberId by remember {
        mutableIntStateOf(
            preferences.getString("memberid", null)?.toIntOrNull() ?: 4
        )
    }
    val memberName by remember {
        mutableStateOf(preferences.getString("name", null) ?: "預設名字preferences")
    }
    forumVM.setItemsVisibility(true)
    // 监听需要显示的消息
    LaunchedEffect(forumVM.postSuccessMessage.value) {
        val message = forumVM.postSuccessMessage.value
        if (message != null) {
            scope.launch {
                val job = launch {
                    hostState.showSnackbar(
                        message = message,
                        duration = SnackbarDuration.Short
                    )
                }
                job.join()
                forumVM.setPostSuccessMessage(null)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
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
                )
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = paddingValues.calculateTopPadding())
                    .background(colorResource(R.color.forum))
            ) {
                item {
                    Spacer(Modifier.height(20.dp))
                    PostDetailContent(postDetail.value)
                    LikeController(forumVM, postDetail.value, memberId, context)
                }

                item {
                    Spacer(Modifier.height(20.dp))
                    AddCommentHeader(comments)
                    AddCommentSection(postDetail.value.postId, forumVM, memberId,memberName)
                }

                item {
                    Spacer(Modifier.height(20.dp))
                    CommentsSection(comments, memberId, navController, forumVM)
                }
            }


            SnackbarHost(
                hostState = hostState,
                modifier = Modifier.align(Alignment.BottomCenter),
                snackbar = { data ->
                    Snackbar(
                        modifier = Modifier
                            .padding(16.dp)
                            .width(200.dp),
                        containerColor = Color.White,
                        contentColor = Color.Black,
                        shape = RoundedCornerShape(16.dp),
                        content = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Icon(
                                    modifier = Modifier
                                        .size(60.dp)
                                        .padding(end = 8.dp),
                                    painter = painterResource(id = R.drawable.dogandcat),
                                    contentDescription = "完成通知"
                                )
                                Text(
                                    text = data.visuals.message,
                                    modifier = Modifier.weight(1f),
                                    fontSize = 20.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    )
                }
            )
        }
    }
}


@Composable
fun PostDetailContent(post: Post) {
    Column(
        Modifier
            .padding(horizontal = 20.dp)
            .background(colorResource(R.color.forum))
    ) {
        // 用戶資料顯示區域
        UserInformationSection(post)
        Spacer(modifier = Modifier.height(15.dp))
        // 貼文標題顯示區域
        PostTitleSection(post)
        Spacer(modifier = Modifier.height(15.dp))

        // 貼文內容顯示區域
        PostBodySection(post)
        Spacer(modifier = Modifier.height(15.dp))

        // 貼文圖片顯示區域
        if (post.imageId != null) PostImageSection(post.imageId)
        Spacer(modifier = Modifier.height(15.dp))
    }
}

@Composable
fun PostImageSection(imageId: Int) {
    Column(
        Modifier
            .height(370.dp)
            .fillMaxWidth()
    ) {
        val imageUrl = remember(imageId) { composeImageUrl(imageId) }
        Log.d("PostImage", "Image URL: $imageUrl") // 檢查URL
        AsyncImage(
            model = (imageUrl),
            contentDescription = "貼文照片",
            modifier = Modifier.fillMaxSize(),
            alignment = Alignment.TopCenter,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.placeholder)
        )
    }
}

@Composable
fun UserInformationSection(post: Post) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(
            modifier = Modifier
                .size(45.dp)
                .background(colorResource(R.color.forumGreen), shape = RoundedCornerShape(5.dp)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val imageResource = R.drawable.catvector
            Image(
                painter = painterResource(id = imageResource),
                contentDescription = "用戶頭貼",
                modifier = Modifier
                    .size(25.dp)
                    .clip(RoundedCornerShape(5.dp))
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(text = post.memberName, fontSize = 16.sp, color = Color.White)
            Spacer(modifier = Modifier.height(3.dp))
            PostDateText(post.createDate)
        }
    }
}

@Composable
fun PostDateText(createDate: String) {
    Text(text = createDate.toFormattedDate(), fontSize = 12.sp, color = Color.Gray)
}

@Composable
fun PostTitleSection(post: Post) {
    Column(modifier = Modifier.padding(start = 5.dp)) {
        Text(text = post.title, fontSize = 25.sp, color = Color.White)
    }
}

@Composable
fun PostBodySection(post: Post) {
    Column(modifier = Modifier.padding(start = 5.dp)) {
        Text(text = post.content, fontSize = 18.sp, color = Color.White)
    }
}

@Composable
fun LikeController(forumVM: ForumVM, post: Post, memberId: Int,context : Context ) {
    var liked by remember { mutableStateOf(forumVM.isPostLikedByMember(post.postId, memberId))}
    var likesCount by remember { mutableIntStateOf(forumVM.getLikesCountForPost(post.postId)) }

    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .height(40.dp)
            .fillMaxWidth(),
    ) {
        // 按讚按鈕
        IconButton(
            onClick = {
                if (liked) {
                    forumVM.unLike(post.postId,memberId)
                    likesCount--
                } else {
                    forumVM.like(post.postId,memberId)
                    likesCount++
                }
                liked =!liked
                Log.d("LikeController", "like: $liked")
            },
            modifier = Modifier.width(55.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = if (liked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "按讚按鈕",
                    modifier = Modifier.size(20.dp),
                    tint = if (liked) Color.Red else Color.LightGray
                )
                Spacer(Modifier.size(5.dp))
                Text(
                    text = likesCount.toString(),
                    color = Color.White
                )
            }
        }
        Spacer(Modifier.weight(1f))
        IconButton(onClick = {

            // 創建分享 Intent
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, "看看這篇有趣的貼文！標題：${post.title}")
            }

            // 創建選擇器
            val chooser = Intent.createChooser(intent, "分享到...")

            // 啟動分享
            try {
                context.startActivity(chooser)
            } catch (e: ActivityNotFoundException) {
                Log.e("Share", "無法處理分享 Intent", e)
            }

        }) {
            Icon(
                imageVector = Icons.Filled.Share,
                contentDescription = "分享",
                modifier = Modifier.size(20.dp),
                tint =Color.LightGray
            )
        }
    }
}


@Composable
fun AddCommentHeader(comments: List<Comment>) {
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 20.dp),
        thickness = 2.dp,
        color = colorResource(R.color.addComment)
    )
    Spacer(Modifier.height(20.dp))
    Row(Modifier.padding(horizontal = 25.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(text = "新增留言", fontSize = 20.sp, color = Color.LightGray)
        Spacer(Modifier.width(15.dp))
        Column(
            modifier = Modifier
                .size(30.dp)
                .background(colorResource(R.color.forumButton), RoundedCornerShape(8.dp)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = comments.size.toString(), fontSize = 20.sp, color = Color.DarkGray)
        }
    }
    Spacer(Modifier.height(20.dp))
}

@Composable
fun AddCommentSection(postId: Int, forumVM: ForumVM, memberId: Int, memberName:String) {
    var commentText by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .background(colorResource(R.color.addComment), shape = RoundedCornerShape(8.dp))
    ) {

        Column {
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(10.dp))
                Column(
                    modifier = Modifier
                        .size(45.dp)
                        .background(
                            colorResource(R.color.forumGreen),
                            shape = RoundedCornerShape(5.dp)
                        ),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val imageResource = R.drawable.catvector
                    Image(
                        painter = painterResource(id = imageResource),
                        contentDescription = "用戶頭貼",
                        modifier = Modifier
                            .size(25.dp)
                            .clip(RoundedCornerShape(5.dp))
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(text = memberName, fontSize = 16.sp, color = Color.White)
                }
            }
        }
        OutlinedTextField(
            value = commentText,
            onValueChange = { if (it.length <= 150) commentText = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(210.dp)
                .verticalScroll(scrollState)
                .padding(top = 60.dp),
            placeholder = { Text("我想說...", color = Color.White) },
            maxLines = 3,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = Color.White
            )
        )

        // 送出按鈕
        Button(
            onClick = {
                val newComment = NewComment(
                    postId = postId,
                    memberId = memberId,
                    content = commentText
                )
                forumVM.addComment(newComment)
                commentText = ""
                forumVM.setPostSuccessMessage("留言成功！")
            },
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.forumButton)),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 15.dp, bottom = 12.dp)
                .height(35.dp)
                .width(100.dp)
        ) {
            Text("送出", color = Color.DarkGray)
        }
    }
}

@Composable
fun CommentsSection(comments: List<Comment>, memberId: Int, navController:NavHostController, forumVM: ForumVM) {
    Column(
        Modifier
            .background(colorResource(R.color.forum))
            .padding(horizontal = 26.dp)
    ) {
        comments.forEachIndexed { index, comment ->
            CommentHeader(comment, isLastComment = (index == comments.size - 1), memberId, navController, forumVM)
            Spacer(modifier = Modifier.height(15.dp))
            CommentContent(comment)
            Spacer(modifier = Modifier.height(15.dp))
            HorizontalDivider(
                thickness = 0.5.dp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(15.dp))
        }
    }
}

@Composable
fun CommentHeader(comment: Comment, isLastComment: Boolean, memberId: Int, navController:NavHostController, forumVM:ForumVM) {
    var showDialog by remember { mutableStateOf(false) }
    var showConfirmDeleteDialog by remember { mutableStateOf(false) }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(
            modifier = Modifier
                .size(45.dp)
                .background(colorResource(R.color.forumGreen), shape = RoundedCornerShape(5.dp)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val imageResource = R.drawable.catvector
            Image(
                painter = painterResource(id = imageResource),
                contentDescription = "用戶頭貼",
                modifier = Modifier
                    .size(25.dp)
                    .clip(RoundedCornerShape(5.dp))
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(text = comment.memberName, fontSize = 16.sp, color = Color.White)
            Spacer(modifier = Modifier.height(3.dp))

            if (isLastComment) {
                Text(
                    text = "最新留言",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            } else {
                CommentDateText(comment.createDate)
            }
        }
        Column(
            Modifier
                .height(45.dp)
                .weight(1f),
            horizontalAlignment = Alignment.End
        ) {
            if(comment.memberId == memberId) {
                IconButton(
                    onClick = {
                        showDialog = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "更多操作",
                        Modifier.size(30.dp),
                        tint = Color.Gray
                    )
                }
            }
        }
    }
    if(showDialog){
        CommentOptionsDialog(
            onDismissRequest = {
                showDialog = false
            },
            onOptionSelected = { actionOption ->
                when (actionOption) {
                    "編輯" -> {
                        forumVM.setSelectedComment(comment)
                        navController.navigate(ForumScreens.CommentEditScreen.name)
                    }
                    "刪除" -> {
                        showConfirmDeleteDialog = true
                    }
                }
                showDialog = false
            }
        )
    }

    if (showConfirmDeleteDialog) {
        DeleteCommentConfirmationDialog(
            onDismissRequest = { showConfirmDeleteDialog = false },
            onConfirmDelete = {
                forumVM.deleteComment(comment.commentId)
                showConfirmDeleteDialog = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteCommentConfirmationDialog(
    onDismissRequest: () -> Unit,
    onConfirmDelete: () -> Unit)
{
    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
        content = {
            Column(
                Modifier
                    .background(Color.LightGray.copy(alpha = 0.9f), shape = RoundedCornerShape(8.dp))
                    .height(240.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(50.dp))
                Text(text = "刪除留言")
                Text(text = "確定要刪除此留言?")
                Spacer(Modifier.height(50.dp))
                Row(
                    Modifier.height(50.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    ActionButton(text = "取消刪除", onClick = onDismissRequest)
                    Spacer(Modifier.width(20.dp))
                    ActionButton(text = "確認刪除", onClick = onConfirmDelete, color = Color.Red)
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentOptionsDialog(onDismissRequest: () -> Unit, onOptionSelected: (String) -> Unit) {
    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
        content = {
            Column(
                Modifier
                    .background(Color.LightGray.copy(alpha = 0.8f), shape = RoundedCornerShape(8.dp))
                    .height(240.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        onOptionSelected("編輯")
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Black
                    ),
                    border = BorderStroke(2.dp, Color.DarkGray),
                    modifier = Modifier.height(70.dp).width(180.dp)
                ) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "編輯")
                    Spacer(Modifier.width(8.dp))
                    Text(text = "編輯留言", fontSize = 18.sp)
                }
                Spacer(Modifier.height(20.dp))
                Button(
                    onClick = {
                        onOptionSelected("刪除")
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Black
                    ),
                    border = BorderStroke(2.dp, Color.DarkGray),
                    modifier = Modifier.height(70.dp).width(180.dp)
                ) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "編輯")
                    Spacer(Modifier.width(8.dp))
                    Text(text = "刪除留言", fontSize = 18.sp)
                }
            }
        }
    )
}

@Composable
fun CommentDateText(createDate: String) {
    Text(
        text = createDate.toFormattedDate(),
        fontSize = 14.sp,
        color = Color.Gray
    )
}

@Composable
fun CommentContent(comment: Comment) {
    Column(Modifier.padding(horizontal = 5.dp)) {
        Text(comment.content, color = Color.White)
    }
}

