package com.example.potel.ui.shopping

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable


/**
 * todo 2-1 將首頁的路由獨立出來
 * */



enum class ShopScreens(title: String){
    Twoclass(title = "類別"),
    Productlist(title = "類別>商品列表"),
    Information(title = "商品列表>商品資訊"),
    Creditcard(title = "商品資訊>信用卡資訊"),
    Ordercheck(title = "信用卡資訊>完成訂單"),
}

fun NavGraphBuilder.shopScreenRoute(navController: NavHostController) {
    composable(
        route = ShopScreens.Twoclass.name,
    ) {
        TwoClassScreen(navController = navController)
    }
    composable(
        route = "${ShopScreens.Productlist.name}/{prdtype}",
    ) { backStackEntry ->
        ProductListScreen(
            navController = navController,
            prdtype = backStackEntry.arguments?.getString("prdtype") ?: "D"
        )
    }
    composable(
        route = ShopScreens.Information.name,
    ) {
        InformationScreen(navController = navController)
    }
    composable(
        route = ShopScreens.Creditcard.name,
    ) {
        CreditCardScreen(navController = navController)
    }
    composable(
        route = ShopScreens.Ordercheck.name,
    ) {
        OrderCheckScreen(navController = navController, onDismissRequest = { /* 自定義行為 */ })
    }
}