package com.example.potel.ui.shopping

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

/**
 * todo 2-1 將首頁的路由獨立出來
 * */

<<<<<<< HEAD:app/src/main/java/com/example/potel/ui/shopping/ShopNavigation.kt
const val SHOP_NAVIGATION_ROUTE = "Shop"

//fun genShopNavigationRoute() = HOME_NAVIGATION_ROUTE

fun NavGraphBuilder.shopScreenRoute(navController: NavHostController) {
    composable(
        route = SHOP_NAVIGATION_ROUTE,
=======




fun NavGraphBuilder.shoppingScreenRoute(navController: NavHostController) {
    composable(
        route = "home",
>>>>>>> JohnnyWu:app/src/main/java/com/example/potel/ui/shopping/HomeNavigation.kt
    ) {
        ShopRoute(navController = navController)
    }
}