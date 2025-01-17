package com.example.potel.ui.forumZone

import android.content.Context
import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
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
import androidx.compose.ui.draw.rotate
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
import com.example.potel.ui.theme.TipColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForumScreen(
    navController: NavHostController,
) {
    val forumVM: ForumVM = viewModel()
    val posts by forumVM.forumsState.collectAsState()

    val context = LocalContext.current
    val preferences = context.getSharedPreferences("member", Context.MODE_PRIVATE)
    val memberId by remember {
        mutableIntStateOf(
            preferences.getString("memberid", null)?.toIntOrNull() ?: 4
        )
    }
    val memberName by remember {
        mutableStateOf(preferences.getString("name", null) ?: "Riley")
    }

    LaunchedEffect(memberId) {
        Log.d("MemberData", "當前的會員 ID 是: $memberId")
        Log.d("MemberData", "當前的會員 name 是: $memberName")
    }
    val scope = rememberCoroutineScope()
    val hostState = remember { SnackbarHostState() }

    // 监听消息并显示
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

    // State to control visibility of items
    val isItemsVisible by forumVM.isItemsVisible.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        // Column or other content for your ForumScreen UI
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color= TipColor.light_brown)
        ) {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "討論區",
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button),
                            tint = Color.White // 设置返回按钮图标为白色
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = colorResource(R.color.forum)
                )
            )
//            HorizontalDivider(
//                Modifier.padding(start = 10.dp, end = 10.dp),
//                thickness = 1.dp,
//                color = Color.DarkGray
//            )
            Spacer(Modifier.height(15.dp))
            if (isItemsVisible) {
                ForumTabContent(forumVM, navController, posts, memberId)
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

        FloatingActionButton(
            onClick = {
                // Hide items when the button is clicked
                forumVM.setItemsVisibility(false)
                // Navigate to the add post screen
                navController.navigate(ForumScreens.PostAddScreen.name)
            },
            elevation = FloatingActionButtonDefaults.elevation(
                pressedElevation = 60.dp,
            ),
            containerColor = colorResource(R.color.forumButton),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "新增貼文",
                modifier = Modifier
                    .border(2.dp, Color.Black, RoundedCornerShape(4.dp))
                    .size(35.dp)
            )
        }
    }
}


@Composable
fun ForumTabContent(
    forumVM: ForumVM,
    navController: NavHostController,
    posts: List<Post>,
    memberId: Int
) {
    val pagerState = rememberPagerState { 1 }
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    Box(Modifier.fillMaxSize()) {
        Column {
            ForumTabSelector(selectedTabIndex) { selectedTabIndex = it }
            Spacer(Modifier.size(5.dp))
            HorizontalPager(state = pagerState) {
                when (selectedTabIndex) {
                    0 -> PostListView(
                        posts,
                        forumVM,
                        navController,
                        showEditButton = false,
                        memberId
                    )

                    1 -> PostListView(
                        posts.filter { it.memberId == memberId },
                        forumVM,
                        navController,
                        showEditButton = true,
                        memberId
                    )
                }
            }
        }
    }
}

@Composable
fun ForumTabSelector(
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    val tabs = listOf("所有貼文", "我的貼文")

    TabRow(
        modifier = Modifier
            .height(40.dp)
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp),
        selectedTabIndex = selectedTabIndex,
        containerColor = Color.Transparent
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = selectedTabIndex == index,
                modifier = Modifier.background(
                    color = if (selectedTabIndex == index)
                        Color.White
                    else
                        colorResource(R.color.forumTab),
                    shape = if (index == 0)
                        RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp)
                    else
                        RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp)
                ),
                onClick = { onTabSelected(index) },
                selectedContentColor = colorResource(R.color.forumTab),
                unselectedContentColor = Color.White,
                text = { Text(title) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostListView(
    posts: List<Post>,
    forumVM: ForumVM,
    navController: NavHostController,
    showEditButton: Boolean,
    memberId: Int
) {
    val coroutineScope = rememberCoroutineScope()
    var isRefreshing by remember { mutableStateOf(false) }


    // 模拟初始加载完成
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            isRefreshing = true
            delay(1000)
            isRefreshing = false
        }
    }

    val onRefresh: () -> Unit = {
        Log.d("PullToRefresh", "Refresh started")
        isRefreshing = true
        coroutineScope.launch {
            delay(1000)
            forumVM.refresh()
            isRefreshing = false
            Log.d("PullToRefresh", "Refresh finished")
        }
    }

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        indicator = {
            CustomRefreshIndicator(isRefreshing)
        }
    ) {
        when {
            !isRefreshing -> {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(
                        if (!showEditButton) posts.shuffled()
                        else posts
                    ) { post ->
                        PostCard(post, forumVM, navController, showEditButton, memberId)
                        HorizontalDivider(
                            Modifier.padding(start = 15.dp, end = 15.dp),
                            thickness = 1.dp,
                            color = TipColor.deep_brown
                        )
                        Spacer(Modifier.height(3.dp))
                    }
                }
            }
        }
    }
}


@Composable
fun CustomRefreshIndicator(isRefreshing: Boolean) {
    val rotation by animateFloatAsState(
        targetValue = if (isRefreshing) 360f else 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )
    Box(modifier = Modifier.fillMaxSize()) {
        if (isRefreshing) {
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .background(color = colorResource(R.color.forumTab), shape = CircleShape)
                    .align(Alignment.Center)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.animalrefresh),
                    contentDescription = "Refreshing",
                    tint = Color.White,
                    modifier = Modifier
                        .size(60.dp)
                        .align(Alignment.Center)
                        .rotate(rotation)
                )
            }
        }
    }
}

@Composable
fun PostCard(
    post: Post,
    forumVM: ForumVM,
    navController: NavHostController,
    showEditButton: Boolean,
    memberId: Int
) {
    val liked by remember { mutableStateOf(forumVM.isPostLikedByMember(post.postId, memberId)) }
    val likesCount by remember { mutableIntStateOf(forumVM.getLikesCountForPost(post.postId)) }
    var showDialog by remember { mutableStateOf(false) }
    var showConfirmDeleteDialog by remember { mutableStateOf(false) }
    val commentCount = forumVM.getCommentCountForPost(post.postId)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable {
                forumVM.setItemsVisibility(false)
                forumVM.setSelectedPost(post)
                navController.navigate(ForumScreens.PostScreen.name)
            }
    ) {
        Row {
            PostHeader(post)
            if (showEditButton) {
                Column(Modifier.height(65.dp)) {
                    Spacer(Modifier.height(8.dp))
                    IconButton(onClick = {
                        showDialog = true
                    }) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = "更多操作",
                            Modifier.size(30.dp),
                            tint = TipColor.deep_brown
                        )
                    }
                }
            }
        }
        Spacer(Modifier.size(10.dp))
        PostContent(post)
        Spacer(Modifier.size(10.dp))
        PostFooter(likesCount, liked, commentCount)
        Spacer(Modifier.size(10.dp))
    }

    if (showDialog) {
        PostOptionsDialog(
            onDismissRequest = {
                showDialog = false
            },
            onOptionSelected = { actionOption ->
                when (actionOption) {
                    "編輯" -> {
                        forumVM.setSelectedPost(post)
                        navController.navigate(ForumScreens.PostEditScreen.name)
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
        DeleteConfirmationDialog(
            onDismissRequest = { showConfirmDeleteDialog = false },
            onConfirmDelete = {
                forumVM.deletePost(post.postId)
                forumVM.setPostSuccessMessage("刪除完成")
                showConfirmDeleteDialog = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteConfirmationDialog(
    onDismissRequest: () -> Unit,
    onConfirmDelete: () -> Unit
) {
    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
        content = {
            Column(
                Modifier
                    .background(
                        Color.LightGray.copy(alpha = 0.9f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .height(240.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(50.dp))
                Text(text = "刪除貼文")
                Text(text = "確定要刪除此貼文?")
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

@Composable
fun ActionButton(
    text: String,
    onClick: () -> Unit,
    color: Color = Color.Black
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = color
        ),
        border = BorderStroke(1.dp, Color.DarkGray),
        modifier = Modifier
            .width(110.dp)
    ) {
        Text(text = text)
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun PostOptionsDialog(onDismissRequest: () -> Unit, onOptionSelected: (String) -> Unit) {
    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
        content = {
            Column(
                Modifier
                    .background(
                        Color.LightGray.copy(alpha = 0.9f),
                        shape = RoundedCornerShape(8.dp)
                    )
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
                    modifier = Modifier
                        .height(70.dp)
                        .width(180.dp)
                ) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "編輯")
                    Spacer(Modifier.width(8.dp))
                    Text(text = "編輯貼文", fontSize = 18.sp)
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
                    modifier = Modifier
                        .height(70.dp)
                        .width(180.dp)
                ) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "編輯")
                    Spacer(Modifier.width(8.dp))
                    Text(text = "刪除貼文", fontSize = 18.sp)
                }
            }
        }
    )
}

@Composable
fun PostHeader(post: Post) {
    Column {
        Spacer(Modifier.size(20.dp))
        Row(
            Modifier
                .height(45.dp)
                .width(390.dp)
                .padding(start = 25.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            MemberImage()
            Column(
                Modifier
                    .weight(1f)
                    .padding(start = 10.dp)
            ) {
                Text(post.memberName, fontSize = 15.sp, color = Color.Black) // 用戶代碼
                Text(
                    post.createDate.toFormattedDate(),
                    color = TipColor.deep_brown,
                    fontSize = 13.sp
                )
            }
        }
    }
}

@Composable
fun PostContent(post: Post) {
    if (post.imageId != null) {
        val truncatedContent = post.content.truncateToLength(60)
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 25.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(post.title,  Modifier.width(250.dp), fontSize = 20.sp, maxLines = 1, color = Color.Black)
                Spacer(Modifier.size(5.dp))
                Text(
                    truncatedContent,
                    Modifier
                        .width(250.dp)
                        .height(50.dp),
                    fontSize = 15.sp,
                    maxLines = 2,
                    color = Color.Black
                )
            }
            Spacer(Modifier.size(20.dp))
            PostImage(post.imageId)
        }
    } else {
        val truncatedContent = post.content.truncateToLength(85)
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 25.dp, end = 25.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    post.title,
                    Modifier.width(400.dp),
                    fontSize = 20.sp,
                    color = Color.Black
                )
                Spacer(Modifier.size(5.dp))
                Text(
                    truncatedContent,
                    Modifier
                        .width(400.dp)
                        .height(50.dp),
                    fontSize = 15.sp,
                    maxLines = 2,
                    color = Color.Black
                )
            }
        }
    }
}


@Composable
fun PostFooter(likesCount: Int, liked: Boolean, commentCount: Int) {
    Row(
        modifier = Modifier.padding(start = 25.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        if (liked) Icon(Icons.Filled.Favorite, contentDescription = "讚數", tint = TipColor.bright_red)
        else Icon(Icons.Filled.FavoriteBorder, contentDescription = "讚數", tint = colorResource(R.color.forum))
        Spacer(Modifier.size(5.dp))
        Text(text = likesCount.toString(), fontSize = 14.sp, color = colorResource(R.color.forum))
        Spacer(Modifier.size(15.dp))
        Icon(Icons.Filled.MailOutline, contentDescription = "留言數", tint = Color.DarkGray)
        Spacer(Modifier.size(5.dp))
        Text(text = commentCount.toString(), fontSize = 14.sp, color = colorResource(R.color.forum))
    }
}

@Composable
fun PostImage(imageId: Int) {
    val imageUrl = remember(imageId) { composeImageUrl(imageId) }
    Log.d("PostImage", "Image URL: $imageUrl")
    AsyncImage(
        model = (imageUrl),
        contentDescription = "貼文照片",
        modifier = Modifier.size(100.dp),
        alignment = Alignment.TopCenter,
        contentScale = ContentScale.Crop,
        placeholder = painterResource(R.drawable.placeholder)
    )
}

@Composable
fun MemberImage() {
    val imageResource = R.drawable.catvector
    Box(
        modifier = Modifier
            .size(45.dp)
            .background(colorResource(R.color.forumGreen), shape = RoundedCornerShape(5.dp)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = imageResource),
            contentDescription = "用戶頭貼",
            modifier = Modifier
                .size(25.dp)
                .clip(RoundedCornerShape(5.dp))
        )
    }
}

fun String.truncateToLength(maxLength: Int): String {
    val sb = StringBuilder()
    var length = 0
    var truncationOccurred = false

    for (char in this) {
        length += if (char.isChinese()) 2 else 1
        if (length > maxLength) {
            truncationOccurred = true
            break
        }
        sb.append(char)
    }

    return if (truncationOccurred) sb.append("...").toString() else sb.toString()
}

fun Char.isChinese(): Boolean {
    return this in '\u4e00'..'\u9fa5'
}





