package com.example.potel.ui.forumZone

import android.annotation.SuppressLint
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.potel.R

@Composable
fun PostScreen(navController: NavHostController) {
    val backStackEntry = navController.getBackStackEntry(ForumScreens.ForumScreen.name)
    val forumVM: ForumVM = viewModel(backStackEntry)
    val postDetail = forumVM.postSelectedState.collectAsState()
    val comments = forumVM.postSelectedCommentsList.collectAsState()
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            Spacer(Modifier.height(20.dp))
            PostDetailContent(postDetail.value)
            LikeController(forumVM,postDetail.value)
        }
        // 顯示新增留言區
        item {
            Spacer(Modifier.height(20.dp))
            AddCommentSection(postDetail.value.postId, forumVM)
        }
        // 顯示留言區
        item {
            Spacer(Modifier.height(20.dp))
            CommentsSection(comments.value)
        }

    }
}

@Composable
fun LikeController(forumVM: ForumVM, post: Post) {
    Column(Modifier.padding(horizontal = 16.dp)) {
        Row(Modifier.height(30.dp)){
            val likecount = forumVM.getLikesCountForPost(post.postId)
            Icon(Icons.Filled.FavoriteBorder, contentDescription = "留言數", tint = Color.Red)
            Text("${likecount}")
        }
    }
}

@Composable
fun PostDetailContent(post: Post) {
    Column(Modifier.padding(horizontal = 16.dp)) {
        // 用戶資料顯示區域
        UserInformationSection(post)
        Spacer(modifier = Modifier.height(10.dp))
        // 貼文標題顯示區域
        PostTitleSection(post)
        Spacer(modifier = Modifier.height(8.dp))

        // 貼文內容顯示區域
        PostBodySection(post)
        Spacer(modifier = Modifier.height(12.dp))

        // 貼文圖片顯示區域
        if(post.ImageId!=null) PostImageSection(post.ImageId)
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun PostImageSection(imageId: Int) {
    Column (
        Modifier
            .padding(horizontal = 5.dp)
            .height(370.dp)
            .fillMaxWidth()
    ) {
        val imageUrl = remember(imageId) { com.example.potel.ui.forumZone.composeImageUrl(imageId)}
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
        Column (
            Modifier
                .background(color = Color.Gray,
                            shape = RoundedCornerShape(5.dp))
                .padding(5.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            val imageResource = R.drawable.room
            Image(
                painter = painterResource(id = imageResource),
                contentDescription = "用戶頭貼",
                modifier = Modifier
                    .size(45.dp)
                    .clip(RoundedCornerShape(5.dp))
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(text = "用戶${post.memberId}", fontSize = 15.sp)
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = post.createDate, fontSize = 12.sp, color = Color.Gray)
        }
    }
}

@Composable
fun PostTitleSection(post: Post) {
    Column(modifier = Modifier.padding(start = 5.dp)) {
        Text(text = post.title, fontSize = 25.sp)
    }
}

@Composable
fun PostBodySection(post: Post) {
    Column(modifier = Modifier.padding(start = 5.dp)) {
        Text(text = post.content, fontSize = 18.sp)
    }
}


@Composable
fun CommentsSection(comments: List<Comment>) {
    Column {
        comments.forEach{ comment ->
            CommentHeader(comment)
            CommentContent(comment)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun CommentHeader(comment: Comment) {
    Column(Modifier.padding(horizontal = 21.dp)
        .padding(start = 15.dp, end = 15.dp)) {
        Text("用戶${comment.memberId}", fontSize = 14.sp)
        Text(text = comment.createDate, fontSize = 12.sp, color = Color.Gray)
    }
}

@Composable
fun CommentContent(comment: Comment) {
    Column(Modifier.padding(horizontal = 21.dp)
        .padding(start = 15.dp, end = 15.dp)) {
        Text(comment.content)
    }
    Spacer(modifier = Modifier.height(12.dp))
    Divider(
        thickness = 0.5.dp,
        color = Color.DarkGray,
        modifier = Modifier.padding( horizontal = 16.dp)
    )
}

@Composable
fun AddCommentSection(postId: Int, forumVM: ForumVM) {
    var commentText by remember { mutableStateOf("") }
    TextField(
        value = commentText,
        onValueChange = { if (it.length <= 150) commentText = it },
        modifier = Modifier
            .padding(start = 21.dp, end = 21.dp)
            .width(370.dp)
            .height(100.dp),
        placeholder = { Text("新增留言...") },
        maxLines =2,
        trailingIcon = {
           Box(
               modifier = Modifier.height(100.dp),
               contentAlignment = Alignment.BottomEnd
           ){
               Button(onClick = {
                   val newComment =NewComment(
                       postId =postId ,
                       memberId = 1,
                       content = commentText
                   )
                   forumVM.addComment(newComment)
                   commentText = ""

               }, shape = RoundedCornerShape(8.dp),
                   border = BorderStroke(width = 1.dp, color = Color.Black),
                   colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                   modifier = Modifier.padding(end = 8.dp, bottom = 8.dp).height(35.dp)
               ) {
                   Text("送出", color = Color.Gray)
               }
           }
        }
    )
}

