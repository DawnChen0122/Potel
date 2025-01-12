package com.example.potel.ui.myorders

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

/**
 * todo 2-1 將首頁的路由獨立出來
 * */

enum class MyOrdersScreens(val title: String){
    MOS01(title = "我的訂單"),
    MOS02(title = "訂房訂單>預約訂單"),
    MOS0201(title = "訂房訂單>預約訂單明細"),
    MOS0202(title = "訂房訂單>取消訂單"),
    MOS03(title = "訂房訂單>歷史訂單"),
    MOS0301(title = "訂房訂單>歷史訂單明細"),
    MOS0302(title = "訂房訂單>我要評分"),
    MOS0303(title = "訂房訂單>我要評分"),
    MOS04(title = "購物訂單>待出貨訂單"),
    MOS0401(title = "購物訂單>待出貨訂單明細"),
    MOS0402(title = "購物訂單>取消訂單"),
    MOS05(title = "購物訂單>歷史訂單"),
    MOS0501(title = "購物訂單>歷史訂單明細")
}


fun NavGraphBuilder.myOrdersScreenRoute(
    navController: NavHostController
) {
    composable(
        route = MyOrdersScreens.MOS01.name,
    ) {
        ScreenMOS01(
            navController = navController,
        )
    }
    composable(
        route = "${MyOrdersScreens.MOS02.name}/{memberid}"
    ) { backStackEntry ->
        ScreenMOS02(
            navController = navController,
            memberid = backStackEntry.arguments?.getString("memberid") ?: "")
    }
    composable(
        route = "${MyOrdersScreens.MOS0201.name}/{orderid}",
    ) { backStackEntry ->
        ScreenMOS0201(
            navController = navController
            , orderid = backStackEntry.arguments?.getString("orderid") ?: ""
        )
    }
    composable(
        route = MyOrdersScreens.MOS0202.name,
    ) {
        ScreenMOS0202(
            navController = navController
        )
    }
    composable(
        route = "${MyOrdersScreens.MOS03.name}/{memberid}"
    ) { backStackEntry ->
        ScreenMOS03(
            navController = navController,
            memberid = backStackEntry.arguments?.getString("memberid") ?: ""
        )
    }
    composable(
        route = "${MyOrdersScreens.MOS0301.name}/{orderid}",
    ) { backStackEntry ->
        ScreenMOS0301(
            navController = navController,
            orderid = backStackEntry.arguments?.getString("orderid") ?: "0"
        )
    }
    composable(
        route = "${MyOrdersScreens.MOS0302.name}/{orderid}",
    ) { backStackEntry ->
        ScreenMOS0302(
            navController = navController,
            orderid = backStackEntry.arguments?.getString("orderid") ?: "0"
        )
    }
    composable(
        route = MyOrdersScreens.MOS0303.name,
    ) {
        ScreenMOS0303(navController = navController)
    }
    composable(
        route = "${MyOrdersScreens.MOS04.name}/{memberid}",
    ) { backStackEntry ->
        ScreenMOS04(
            navController = navController,
            memberid = backStackEntry.arguments?.getString("memberid") ?: "0"
        )
    }
    composable(
        route = "${MyOrdersScreens.MOS0401.name}/{prdorderid}",
    ) { backStackEntry ->
        ScreenMOS0401(
            navController = navController,
            prdorderid = backStackEntry.arguments?.getString("prdorderid") ?: "0"
        )
    }
    composable(
        route = "${MyOrdersScreens.MOS0402.name}/{prdorderid}",
    ) { backStackEntry ->
        ScreenMOS0402(
            navController = navController,
            prdorderid = backStackEntry.arguments?.getString("prdorderid") ?: "0"
            )
    }
    composable(
        route = "${MyOrdersScreens.MOS05.name}/{memberid}",
    ) { backStackEntry ->
        ScreenMOS05(
            navController = navController,
            memberid = backStackEntry.arguments?.getString("memberid") ?: "0"
        )
    }
    composable(
        route = "${MyOrdersScreens.MOS0501.name}/{prdorderid}",
    ) { backStackEntry ->
        ScreenMOS0501(
            navController = navController,
            prdorderid = backStackEntry.arguments?.getString("prdorderid") ?: "0"
        )
    }
}