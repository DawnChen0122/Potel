package com.example.potel.ui.pets

import android.icu.text.CaseMap.Title
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.potel.ui.myorders.ScreenMOS01
import com.example.potel.ui.myorders.ScreenMOS02
import com.example.potel.ui.myorders.ScreenMOS0201

/**
 * todo 2-1 將首頁的路由獨立出來
 * */
enum class Screens(title: String){
    PetsFirst(title = "新增寵物(狗貓)頁面"),
    PetsAdd(title = "新增資訊頁面"),
    PetsPreview(title = "看到所有寵物")
}
const val PETS_NAVIGATION_ROUTE = "home"

//
//fun NavGraphBuilder.PetsScreenRoute(navController: NavHostController) {
//    composable(
//        route = com.example.potel.ui.myorders.Screens.MOS01.name,
//    ) {
//        ScreenMOS01(
//            navController = navController
//        )
//    }
//    composable(
//        route = com.example.potel.ui.myorders.Screens.MOS02.name,
//    ) {
//        ScreenMOS02(navController = navController)
//    }
//    composable(
//        route = com.example.potel.ui.myorders.Screens.MOS0201.name,
//    ) {
//        ScreenMOS0201(navController = navController)
//    }
//}