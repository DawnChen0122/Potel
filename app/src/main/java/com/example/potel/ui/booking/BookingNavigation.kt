//package com.example.potel.ui.booking
//
//import androidx.navigation.NavGraphBuilder
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.composable
//import com.example.potel.ui.`pets file`.PETS_NAVIGATION_ROUTE
//import com.example.potel.ui.`pets file`.PetsScreenRoute
//
///**
// * todo 2-1 將首頁的路由獨立出來
// * */
//
//const val HOME_NAVIGATION_ROUTE = "booking"
//
//fun getPetsNavigationRoute() = PETS_NAVIGATION_ROUTE
//
//fun NavGraphBuilder.PetsScreenRoute(navController: NavHostController) {
//    composable(
//        route = PETS_NAVIGATION_ROUTE,
//    ) {
//        PetsScreenRoute(navController = navController)
//    }
//}