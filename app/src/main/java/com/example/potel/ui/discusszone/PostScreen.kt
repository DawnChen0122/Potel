package com.example.potel.ui.discusszone

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.potel.ui.discussZone.ForumScreens

@Composable
fun PostScreen(
    navController: NavHostController,
) {
    val backStackEntry = navController.getBackStackEntry(ForumScreens.ForumScreen.name)
    val forumVM: ForumVM = viewModel(backStackEntry)
    val postDetail = forumVM.postSelectedState.collectAsState()

    LazyColumn (modifier = Modifier.fillMaxSize()){
        item {
            Spacer(Modifier.height(20.dp))
            PostDetailContent(postDetail.value)
        }
    }
}

@Composable
fun PostDetailContent(post: Post) {

}
