package com.example.potel.ui.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.potel.ui.account.Edit
import com.example.potel.ui.account.Login
import com.example.potel.ui.account.Resetpassword
import com.example.potel.ui.account.Signup

enum class AccountScreens(val title: String) {

    Signup(title = "註冊畫面"),
    Login(title = "登入畫面"),
    Reset(title = "重設密碼"),
    HomeRoute(title = "首頁"),
    Edit(title = "編輯會員資料"),
}

fun NavGraphBuilder.accountRoute(navController: NavHostController) {
    composable(
        route = AccountScreens.HomeRoute.name,
    ) {

        HomeRoute(navController = navController)
    }


    composable(
        route = AccountScreens.Signup.name
    ) {
        Signup(
            navController = navController
        )
    }

    composable(
        route = AccountScreens.Login.name
    ) {
        Login(
            navController = navController
        )
    }

    composable(
        route = AccountScreens.Reset.name
    ) {
        Resetpassword(
            navController = navController
        )
    }

    composable(
        route = AccountScreens.Edit.name
    ) {
        Edit(
            navController = navController
        )
    }


}