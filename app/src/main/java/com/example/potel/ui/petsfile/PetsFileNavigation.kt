package com.example.potel.ui.petsfile

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable


/**
 * todo 2-1 將首頁的路由獨立出來
 * */
enum class Screens(title: String){
    PetsFileFirst(title = "新增寵物(狗貓)頁面"),
    PetsFileAdd(title = "新增資訊頁面"),
    PetsFilePreview(title = "看到所有寵物")
}
const val PETSFILES_NAVIGATION_ROUTE = "home"


fun NavGraphBuilder.petsfileScreenRoute(navController: NavHostController) {
    composable(
        route = Screens.PetsFileFirst.name,
    ) {
        ScreenPetsFileFirst(
            navController = navController
        )
    }
    composable(
        route = Screens.PetsFileAdd.name,
    ) {
        ScreenPetsFileAdd(navController = navController)
    }
    composable(
        route = Screens.PetsFilePreview.name,
    ) {
        ScreenPetsFilePreview(navController = navController)
    }
}