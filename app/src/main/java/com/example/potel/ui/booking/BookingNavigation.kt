package com.example.potel.ui.booking

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

/**
 * todo 2-1 將首頁的路由獨立出來
 * */

const val HOME_NAVIGATION_ROUTE = "booking"

fun genHomeNavigationRoute() = HOME_NAVIGATION_ROUTE

fun NavGraphBuilder.bookingScreenRoute(navController: NavHostController) {
    composable(
        route = HOME_NAVIGATION_ROUTE,
    ) {
        BookingRoute(navController = navController)
    }
}