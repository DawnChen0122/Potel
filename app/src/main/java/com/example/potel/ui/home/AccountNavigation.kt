package com.example.potel.ui.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

enum class LogIn (title: String){
    LogIn(title = "登入畫面")
}

fun NavGraphBuilder.homeScreen1Route(navController: NavHostController) {
    composable(
        route = Screens.MOS01.name,
    ) {
        ScreenMOS01(
            navController = navController
        )
    }
}