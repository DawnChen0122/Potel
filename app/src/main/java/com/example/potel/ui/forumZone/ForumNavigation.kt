package com.example.potel.ui.forumZone

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

enum class ForumScreens(val title: String) {
    ForumScreen(title = "討論區"),
    PostScreen(title = "文章"),
    PostEditScreen(title = "編輯文章"),
    PostAddScreen(title = "新增文章"),
    CommentEditScreen(title = "編輯留言")
}

fun NavGraphBuilder.forumScreenRoute(navController: NavHostController) {
    composable(
        route = ForumScreens.ForumScreen.name,
    ) {
        ForumScreen(navController = navController)
    }
    composable(
        route = ForumScreens.PostScreen.name,
    ) {
        PostScreen(navController = navController)
    }
    composable(
        route = ForumScreens.PostEditScreen.name,
    ) {
        PostEditScreen(navController = navController)
    }
    composable(
        route = ForumScreens.PostAddScreen.name,
    ) {
        PostAddScreen(navController = navController)
    }
    composable(
        route = ForumScreens.CommentEditScreen.name,
    ) {
        CommentEditScreen(navController = navController)
    }
}



