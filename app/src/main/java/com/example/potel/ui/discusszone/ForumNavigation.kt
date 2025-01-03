package com.example.potel.ui.discussZone

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.potel.ui.discusszone.CommentEditScreen
import com.example.potel.ui.discusszone.ForumScreen
import com.example.potel.ui.discusszone.PostAddScreen
import com.example.potel.ui.discusszone.PostEditScreen
import com.example.potel.ui.discusszone.PostScreen

enum class ForumScreens(title: String){
    ForumScreen(title = "討論區"),
    PostScreen(title="文章"),
    PostEditScreen(title="編輯文章"),
    PostAddScreen(title="新增文章"),
    CommentEditScreen(title="編輯留言")
}

fun NavGraphBuilder.ForumScreenRoute(navController: NavHostController) {

    composable(
        route = ForumScreens.ForumScreen.name
    ) {
       ForumScreen(navController)
    }
    composable(
        route = ForumScreens.PostScreen.name
    ) {
        PostScreen(navController)
    }
    composable(
        route = ForumScreens.PostEditScreen.name
    ) {
        PostEditScreen(navController)
    }
    composable(
        route = ForumScreens.PostAddScreen.name
    ) {
        PostAddScreen(navController)
    }
    composable(
        route = ForumScreens.CommentEditScreen.name
    ) {
        CommentEditScreen(navController)
    }


}


