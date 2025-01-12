package com.example.potel.ui.shopping

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

/**
 * todo 2-1 將首頁的路由獨立出來
 * */

fun NavGraphBuilder.shoppingScreenRoute(navController: NavHostController) {
    composable(
        route = "home",
    ) {
        ShopRoute(navController = navController)
    }
}