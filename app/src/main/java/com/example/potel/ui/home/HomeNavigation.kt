package com.example.potel.ui.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
//import com.example.potel.ui.account.Edit
import com.example.potel.ui.account.Login
import com.example.potel.ui.account.Resetpassword
//import com.example.potel.ui.account.Resetpassword2
import com.example.potel.ui.account.Signup

/**
 * todo 2-1 將首頁的路由獨立出來
 * */


enum class AccountScreens(val title: String){

    Signup (title = "註冊畫面"),
    Login (title = "登入畫面"),
    Reset (title = "重設密碼"),
    HomeRoute (title = "首頁"),
    Edit (title = "編輯會員資料"),
//    Change(title="變更密碼" )
}


fun NavGraphBuilder.accountRoute(navController: NavHostController) {
    composable(
        route =  AccountScreens.HomeRoute.name,
    ) {

//        Text(
//            modifier = Modifier.clickable{
//                navController.navigate(Screens.Login.name)
//            }, text = "登入")
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

//    composable(
//        route = AccountScreens.Edit.name
//    ) {
//        Edit(
//            navController = navController
//        )
//    }


//    composable(
//        route = AccountScreens.Change.name
//    ) {
//        Resetpassword2(
//            navController = navController
//        )
//    }


}