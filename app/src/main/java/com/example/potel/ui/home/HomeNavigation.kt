package com.example.potel.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.potel.ui.account.Screens

/**
 * todo 2-1 將首頁的路由獨立出來
 * */

const val HOME_NAVIGATION_ROUTE = "home"

fun genHomeNavigationRoute() = HOME_NAVIGATION_ROUTE

fun NavGraphBuilder.homeScreenRoute(navController: NavHostController) {
    composable(
        route = HOME_NAVIGATION_ROUTE,
    ) {

        Text(
            modifier = Modifier.clickable{
                navController.navigate(Screens.Login.name)
            }, text = "登入")
        HomeRoute(navController = navController)
    }
}