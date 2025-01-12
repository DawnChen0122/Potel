package com.example.potel.ui.shopping

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable


/**
 * todo 2-1 將首頁的路由獨立出來
 * */


const val SHOP_NAVIGATION_ROUTE = "Shop"

//fun genShopNavigationRoute() = HOME_NAVIGATION_ROUTE

enum class ShopScreens(title: String){
    twoclass(title = "類別"),
    productlist(title = "類別>商品列表"),
    information(title = "商品列表>商品資訊"),
    creditcard(title = "商品資訊>信用卡資訊"),
    ordercheck(title = "信用卡資訊>完成訂單"),
}


fun NavGraphBuilder.shoppingScreenRoute(navController: NavHostController) {
    composable(
        route = "home",
    ) {
        TwoClassScreen(navController = navController)
    }
    composable(
        route = ShopScreens.productlist.name,
    ) {
        ProductListScreen(navController = navController)
    }
    composable(
        route = ShopScreens.information.name,
    ) {
        InformationScreen(navController = navController)
    }
    composable(
        route = ShopScreens.creditcard.name,
    ) {
        CreditCardScreen(navController = navController)
    }
    composable(
        route = ShopScreens.ordercheck.name,
    ) {
        OrderCheckScreen(navController = navController, onDismissRequest = { /* 自定義行為 */ })
    }
}

