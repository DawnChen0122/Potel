package com.example.potel.ui.discussZone

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

val DISCUSSZONE_NAVIGATION_ROUTE = "discussZone"

fun genDiscussZoneNavigationRoute() = DISCUSSZONE_NAVIGATION_ROUTE

fun NavGraphBuilder.discussZoneScreenRoute(navController: NavHostController) {
    composable(
        route = DISCUSSZONE_NAVIGATION_ROUTE,
    ) {
        DiscussZoneRoute(navController = navController)
    }
}


