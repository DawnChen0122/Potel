package com.example.potel.ui.myorders

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
//import java.text.SimpleDateFormat
//import java.util.Date

/**
 * todo 2-1 將首頁的路由獨立出來
 * */

const val MYORDERS_NAVIGATION_ROUTE = "myorders"

fun genMyOrdersNavigationRoute() = MYORDERS_NAVIGATION_ROUTE

fun NavGraphBuilder.myOrdersScreenRoute(navController: NavHostController) {
    composable(
        route = MYORDERS_NAVIGATION_ROUTE,
    ) {
//        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//        sdf.format(Date())
        MyOrdersRoute(navController = navController)
    }
}