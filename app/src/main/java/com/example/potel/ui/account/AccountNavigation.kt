package com.example.potel.ui.account

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
//import java.text.SimpleDateFormat
//import java.util.Date

enum class Screens(title: String){
    Signup(title = "註冊畫面")
}

fun NavGraphBuilder.accountRoute(navController: NavHostController) {
    composable(
        route = Screens.Signup.name
    ) {
        SignUp(
            navController = navController
        )
    }
}