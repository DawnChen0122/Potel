package com.example.potel.ui.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.potel.ui.account.Login
import com.example.potel.ui.home.*

enum class Screens(title: String){
    LogIn(title = "登入")
}

fun NavGraphBuilder.homeScreen1Route(navController: NavHostController) {
    composable(
        route = Screens.LogIn.name,
    ) {
        Login (
            navController = navController
        )
    }
}