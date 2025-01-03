package com.example.potel.ui.discusszone

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

    Column(Modifier.fillMaxSize()) {
        Spacer(Modifier.height(10.dp))
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
                //fix me postaddscreen
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
        modifier = Modifier.height(40.dp),
        selectedTabIndex = selectedTabIndex
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = selectedTabIndex == index,
                modifier = Modifier.background(
                    color = if (selectedTabIndex == index)
                        Color.Blue
                    else
                        Color.LightGray
                ),
                onClick = { onTabSelected(index) },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.Yellow,
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
            HorizontalDivider()
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
    var showDialog by remember { mutableStateOf(false) }
    val likesCount = forumVM.getLikesCountForPost(post.postId)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .border(1.dp, Color.Gray, RoundedCornerShape(30.dp))
            .clickable {
                forumVM.setSelectedPost(post)
                //fix me nav postScreen
            }
    ) {
        Spacer(Modifier.size(20.dp))
        PostHeader(post, showEditButton) { showDialog = true }
        Spacer(Modifier.size(10.dp))
        PostContent(post)
        Spacer(Modifier.size(10.dp))
        PostFooter(post, likesCount)
        Spacer(Modifier.size(10.dp))
    }
    if (showDialog) {
        PostOptionsDialog(
            post = post,
            forumVM = forumVM,
            navController = navController,
            onDismiss = { showDialog = false }
        )
    }
}

@Composable
fun PostHeader(post: Post, showEditButton: Boolean, onMoreClick: () -> Unit) {
    Row(
        Modifier
            .height(45.dp)
            .fillMaxWidth()
            .padding(start = 25.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MemberImage(post.postImageId)
//        fix me 用戶頭貼
        Column(
            Modifier
                .weight(1f)
                .padding(start = 10.dp)
        ) {
            Text("用戶代碼 : ${post.memberId}", fontSize = 15.sp) //fix me
            Text(post.createDate, color = Color.LightGray, fontSize = 13.sp)
        }
        if (showEditButton) {
            IconButton(
                onClick = onMoreClick,
                ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowDown,
                    contentDescription = "更多操作",
                    Modifier.size(30.dp),
                    tint = Color.Gray
                )
            }
        }
    }
}

@Composable
fun PostContent(post: Post) {
    val truncatedContent = post.content.truncateToLength(45)

    Row(
        Modifier
            .fillMaxWidth()
            .padding(start = 25.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(post.title, fontSize = 20.sp, maxLines = 1)
            Spacer(Modifier.size(5.dp))
            Text(truncatedContent, Modifier.width(235.dp), fontSize = 17.sp, maxLines = 2)
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
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Filled.Favorite, contentDescription = "留言數", tint = Color.Gray)
        Spacer(Modifier.size(5.dp))
        Text(text = "${likesCount} ", fontSize = 14.sp)
        Spacer(Modifier.size(10.dp))
    }
}

@Composable
fun PostImage(postImageId: Int?, modifier: Modifier = Modifier) {
    val imageResource = R.drawable.ic_launcher_background
    Image(
        painter = painterResource(id = imageResource),
        contentDescription = "用戶頭貼",
        modifier = modifier
            .size(100.dp)
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
            .background(Color.LightGray, RoundedCornerShape(5.dp))
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
fun PostCardPreview() {
    // 假資料
    val fakePost =
        Post(postId = 1, title = "First Post", content = "This is the content of the first post.")
    val fakeForumVM = ForumVM()

    PostCard(
        post = fakePost,
        forumVM = fakeForumVM,
        navController = rememberNavController(),
        showEditButton = true
    )
}





