package com.example.potel.ui.account

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable



enum class Screens(title: String){
    Signup(title = "註冊畫面"),
    Login(title = "登入畫面")
}

fun NavGraphBuilder.accountRoute(navController: NavHostController) {
    composable(
        route = Screens.Signup.name
    ) {
        Signup(
            navController = navController
        )
    }
    composable(
        route = Screens.Login.name
    ) {
        LogIn(
            navController = navController
        )
    }
}