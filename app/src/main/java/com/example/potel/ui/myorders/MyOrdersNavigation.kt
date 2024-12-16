package com.example.potel.ui.myorders

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import idv.fan.tibame.tip102.ui.feature.home.HOME_NAVIGATION_ROUTE

/**
 * todo 2-1 將首頁的路由獨立出來
 * */

const val MYORDERS_NAVIGATION_ROUTE = "myorders"

fun genMyOrdersNavigationRoute() = MYORDERS_NAVIGATION_ROUTE

fun NavGraphBuilder.myOrdersScreenRoute(navController: NavHostController) {
    composable(
        route = MYORDERS_NAVIGATION_ROUTE,
    ) {
        MyOrdersRoute(navController = navController)
    }
}