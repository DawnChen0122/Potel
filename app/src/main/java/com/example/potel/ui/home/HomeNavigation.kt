package com.example.potel.ui.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.potel.ui.account.Login
import com.example.potel.ui.account.Resetpassword
import com.example.potel.ui.account.Signup

/**
 * todo 2-1 將首頁的路由獨立出來
 * */


enum class Screens(val title: String){

    Signup (title = "註冊畫面"),
    Login (title = "登入畫面"),
    Resetpassword (title = "重設密碼"),
    HomeRoute (title = "重設密碼")
}


fun NavGraphBuilder.accountRoute(navController: NavHostController) {
    composable(
        route =  Screens.HomeRoute.name,
    ) {

//        Text(
//            modifier = Modifier.clickable{
//                navController.navigate(Screens.Login.name)
//            }, text = "登入")
        HomeRoute(navController = navController)
    }


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
        Login(
            navController = navController
        )
    }

    composable(
        route = Screens.Resetpassword.name
    ) {
        Resetpassword(
            navController = navController
        )
    }
}