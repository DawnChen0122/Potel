package com.example.potel.ui.forumZone

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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.potel.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForumScreen(
    navController: NavHostController,
) {
    val forumVM: ForumVM = viewModel()
    val posts by forumVM.forumsState.collectAsState()
    val memberId = 5

    Column(
        Modifier
            .fillMaxSize()
            .background(colorResource(R.color.forum))
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "討論區",
                    color = Color.White // 設置文字顏色為白色
                )
            },
            navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button),
                        tint = Color.White // 設置返回按鈕圖標為白色
                    )
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = colorResource(R.color.forum) // 背景色設置
            )
        )
        HorizontalDivider(
            Modifier.padding(start = 10.dp, end = 10.dp),
            thickness = 1.dp,
            color = Color.DarkGray
        )
        Spacer(Modifier.height(20.dp))
        ForumTabContent(forumVM, navController, posts, memberId)
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
                        posts.filter { it.memberId == memberId }, // fix me
                        forumVM,
                        navController,
                        showEditButton = true,
                        memberId
                    )
                }
            }
        }
        FloatingActionButton(
            onClick = {
                navController.navigate(ForumScreens.PostAddScreen.name)
            },
            containerColor = colorResource(R.color.foruButton),
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
fun ForumTabSelector(
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    val tabs = listOf("所有貼文", "我的貼文")

    TabRow(
        modifier = Modifier
            .height(40.dp)
            .fillMaxWidth()
            .background(colorResource(R.color.forum))
            .padding(start = 15.dp, end = 15.dp),
        selectedTabIndex = selectedTabIndex,
        containerColor = Color.Transparent
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = selectedTabIndex == index,
                modifier = Modifier.background(
                    color = if (selectedTabIndex == index)
                        colorResource(R.color.forumTab)
                    else
                        Color.White,
                    shape = if (index == 0)
                        RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp)
                    else
                        RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp)
                ),
                onClick = { onTabSelected(index) },
                selectedContentColor = Color.White,
                unselectedContentColor = colorResource(R.color.forumTab),
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
    val onRefresh: () -> Unit = {
        Log.d("PullToRefresh", "Refresh started")
        isRefreshing = true
        coroutineScope.launch {
            delay(1000) // 模擬刷新操作
            forumVM.refresh()
            isRefreshing = false
            Log.d("PullToRefresh", "Refresh finished")
        }
    }
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        indicator = {
            CustomRefreshIndicator(isRefreshing = isRefreshing)
        }
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(posts) { post ->
                PostCard(post, forumVM, navController, showEditButton, memberId)
                HorizontalDivider(
                    Modifier.padding(start = 15.dp, end = 15.dp),
                    thickness = 1.dp,
                    color = Color.DarkGray
                )
                Spacer(Modifier.height(3.dp))
            }
        }
    }
}

@Composable
fun CustomRefreshIndicator(isRefreshing: Boolean) {
    val rotation by animateFloatAsState(
        targetValue = if (isRefreshing) 360f else 0f, // 如果正在刷新，圖片旋轉一圈
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )
    Box(modifier = Modifier.fillMaxSize()) {
        if (isRefreshing) {
            Box(
                modifier = Modifier
                    .size(50.dp) // 背景的總大小
                    .background(color = colorResource(R.color.foruButton), shape = CircleShape)
                    .align(Alignment.TopCenter)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.animalrefresh), // 使用內建的刷新圖標
                    contentDescription = "Refreshing",
                    tint = Color.Black, // 自定義顏色
                    modifier = Modifier
                        .size(40.dp) // 圖標大小
                        .align(Alignment.Center)
                        .rotate(rotation) // 添加旋轉動畫
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
            .background(colorResource(R.color.forum))
            .padding(5.dp)
            .clickable {
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
                            tint = Color.Gray
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
                .width(350.dp)
                .padding(start = 25.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            MemberImage() // 用戶頭貼
            Column(
                Modifier
                    .weight(1f)
                    .padding(start = 10.dp)
            ) {
                Text("用戶代碼 : ${post.memberId}", fontSize = 15.sp, color = Color.White) // 用戶代碼
                Text(
                    post.createDate.toFormattedDate(),
                    color = colorResource(R.color.forumTab),
                    fontSize = 13.sp
                ) // 日期
            }
        }
    }
}

@Composable
fun PostContent(post: Post) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(start = 25.dp)
            .background(colorResource(R.color.forum)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (post.imageId != null) {
            val truncatedContent = post.content.truncateToLength(60)
            Column {
                Text(post.title, fontSize = 20.sp, maxLines = 1, color = Color.White)
                Spacer(Modifier.size(5.dp))
                Text(
                    truncatedContent,
                    Modifier
                        .width(250.dp)
                        .height(50.dp),
                    fontSize = 15.sp,
                    maxLines = 2,
                    color = Color.White
                )
            }
            Spacer(Modifier.size(20.dp))
            PostImage(post.imageId)
        } else {
            val truncatedContent = post.content.truncateToLength(85)
            Column {
                Text(post.title, fontSize = 20.sp, maxLines = 1, color = Color.White)
                Spacer(Modifier.size(5.dp))
                Text(
                    truncatedContent,
                    Modifier
                        .width(370.dp)
                        .height(50.dp),
                    fontSize = 15.sp,
                    maxLines = 2,
                    color = Color.White
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
        if (liked) Icon(Icons.Filled.Favorite, contentDescription = "讚數", tint = Color.Red)
        else Icon(Icons.Filled.FavoriteBorder, contentDescription = "讚數", tint = Color.White)
        Spacer(Modifier.size(5.dp))
        Text(text = likesCount.toString(), fontSize = 14.sp, color = Color.White)
        Spacer(Modifier.size(15.dp))
        Icon(Icons.Filled.MailOutline, contentDescription = "留言數", tint = Color.LightGray)
        Text(text = commentCount.toString(), fontSize = 14.sp, color = Color.White)
    }
}

@Composable
fun PostImage(imageId: Int) {
    val imageUrl = remember(imageId) { composeImageUrl(imageId) }
    Log.d("PostImage", "Image URL: $imageUrl") // 檢查URL
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




