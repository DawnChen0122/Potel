package com.example.potel.ui.forumZone

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.potel.R


@Composable
fun ForumScreen(
    navController: NavHostController,
) {
    val forumVM: ForumVM = viewModel()
    val posts by forumVM.forumsState.collectAsState()

    Column(Modifier
            .fillMaxSize()
            .background(colorResource(R.color.forum))
    ) {
        Spacer(Modifier.height(20.dp))
        ForumTabContent(forumVM, navController, posts)
    }
}

@Composable
fun ForumTabContent(
    forumVM: ForumVM,
    navController: NavHostController,
    posts: List<Post>
) {
    val pagerState = rememberPagerState { 2 }
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Box(Modifier.fillMaxSize()) {
        Column {
            ForumTabSelector(selectedTabIndex) { selectedTabIndex = it }
            Spacer(Modifier.size(5.dp))
            HorizontalPager(state = pagerState) {
                when (selectedTabIndex) {
                    0 -> PostListView(posts, forumVM, navController, showEditButton = false)
                    1 -> PostListView(
                        posts.filter { it.memberId == 1 }, //fix me
                        forumVM,
                        navController,
                        showEditButton = true
                    )
                }
            }
        }
        FloatingActionButton(
            onClick = {
                navController.navigate(ForumScreens.PostAddScreen.name)
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "新增貼文"
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
            .padding(start = 15.dp, end =15.dp),
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
                    shape = if(index == 0)
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

@Composable
fun PostListView(
    posts: List<Post>,
    forumVM: ForumVM,
    navController: NavHostController,
    showEditButton: Boolean = false
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(posts) { post ->
            PostCard(post, forumVM, navController, showEditButton)
            Divider(Modifier.padding(start = 15.dp, end = 15.dp),thickness = 1.dp, color = Color.DarkGray)
            Spacer(Modifier.height(3.dp))
        }
    }
}

@Composable
fun PostCard(
    post: Post,
    forumVM: ForumVM,
    navController: NavHostController,
    showEditButton: Boolean
) {
    val likesCount = forumVM.getLikesCountForPost(post.postId)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(R.color.forum))
            .padding(5.dp)
            .clickable {
                forumVM.setSelectedPost(post)
                //fix me nav postScreen
                navController.navigate(ForumScreens.PostScreen.name)
            }
    ) {
        Row{
            PostHeader(post)
            EditDialog(showEditButton)
        }
        Spacer(Modifier.size(10.dp))
        PostContent(post)
        Spacer(Modifier.size(10.dp))
        PostFooter(post, likesCount)
        Spacer(Modifier.size(10.dp))
    }
}

@Composable
fun EditDialog(showEditButton: Boolean) {
    Column (
        Modifier.height(65.dp)
    ) {
        Spacer(Modifier.height(5.dp))
        if (showEditButton) {
            IconButton(
                onClick ={ }
            ) {
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
            MemberImage(post.postImageId)
            // 用戶頭貼
            Column(
                Modifier
                    .weight(1f)
                    .padding(start = 10.dp)
            ) {
                Text("用戶代碼 : ${post.memberId}", fontSize = 15.sp, color = Color.White) // 用戶代碼
                Text(post.createDate, color = colorResource(R.color.forumTab), fontSize = 13.sp) // 日期
            }
        }
    }
}

@Composable
fun PostContent(post: Post) {
    val truncatedContent = post.content.truncateToLength(55)

    Row(
        Modifier
            .fillMaxWidth()
            .padding(start = 25.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(post.title, fontSize = 20.sp, maxLines = 1, color = Color.White)
            Spacer(Modifier.size(5.dp))
            Text(truncatedContent, Modifier.width(235.dp), fontSize = 15.sp, maxLines = 2, color = Color.White)
        }
        Spacer(Modifier.size(20.dp))
        PostImage(post.postImageId)
        //fix me 貼文照片
    }
}

@Composable
fun PostFooter(post: Post, likesCount: Int) {
    Row(
        modifier = Modifier.padding(start = 25.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(Icons.Filled.FavoriteBorder, contentDescription = "留言數", tint = Color.Red)
        Spacer(Modifier.size(5.dp))
        Text(text = "${likesCount} ", fontSize = 14.sp, color = Color.White)
        Spacer(Modifier.size(10.dp))
    }
}

@Composable
fun PostImage(postImageId: Int?, modifier: Modifier = Modifier) {
    val imageResource = R.drawable.ic_launcher_background
    Image(
        painter = painterResource(id = imageResource),
        contentDescription = "貼文照片",
        modifier = modifier
            .size(100.dp)
            .clip(RoundedCornerShape(5.dp))
            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
    )
}

@Composable
fun MemberImage(userImg: Int?) {
    val imageResource = R.drawable.room

    Image(
        painter = painterResource(id = imageResource),
        contentDescription = "用戶頭貼",
        modifier = Modifier
            .size(45.dp)
            .clip(RoundedCornerShape(5.dp))
    )
}

@Composable
fun PostOptionsDialog(
    post: Post,
    forumVM: ForumVM,
    navController: NavHostController,
    onDismiss: () -> Unit
) {

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

    // 如果截斷發生，則在結尾加上 "..."
    return if (truncationOccurred) sb.append("...").toString() else sb.toString()
}

fun Char.isChinese(): Boolean {
    return this in '\u4e00'..'\u9fa5'
}


@Preview(showBackground = true)
@Composable
fun ForumScreenPreview() {
    // 使用假資料來進行預覽
    val fakeForumVM = ForumVM()
    val fakePosts = listOf(
        Post(postId = 1, title = "First Post", content = "This is the content of the first post."),
        Post(postId = 2, title = "Second Post", content = "This is the content of the second post.")
    )

    // 渲染 ForumScreen，傳入假的資料
    ForumScreen(navController = rememberNavController())
}

@Preview(showBackground = true)
@Composable
fun ForumTabContentPreview() {
    // 假資料，傳入預設的 forumVM 和假資料
    val fakeForumVM = ForumVM()
    val fakePosts = listOf(
        Post(postId = 1, title = "First Post", content = "This is the content of the first post."),
        Post(postId = 2, title = "Second Post", content = "This is the content of the second post.")
    )

    ForumTabContent(
        forumVM = fakeForumVM,
        navController = rememberNavController(),
        posts = fakePosts
    )
}

@Preview(showBackground = true)
@Composable
fun PostListViewPreview() {
    // 假資料
    val fakeForumVM = ForumVM()
    val fakePosts = listOf(
        Post(postId = 1, title = "First Post", content = "This is the content of the first post."),
        Post(postId = 2, title = "Second Post", content = "This is the content of the second post.")
    )

    PostListView(
        posts = fakePosts,
        forumVM = fakeForumVM,
        navController = rememberNavController(),
        showEditButton = true
    )
}

@Preview(showBackground = true)
@Composable
fun PostCardPreview() {
    // 假資料
    val fakePost = Post(postId = 1, title = "First Post", content = "This is the content of the first post.")
    val fakeForumVM = ForumVM()

    PostCard(
        post = fakePost,
        forumVM = fakeForumVM,
        navController = rememberNavController(),
        showEditButton = false
    )
}




