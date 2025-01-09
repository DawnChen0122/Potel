package com.example.potel.ui.booking

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.potel.ui.booking.BookingScreens.Booking
import com.example.potel.ui.booking.BookingScreens.DateSelection
import com.example.potel.ui.booking.BookingScreens.RoomSelection
import com.example.potel.ui.booking.BookingScreens.Payment
import com.example.potel.ui.booking.BookingScreens.BookingSuccess


// title 去哪裡改？

/**
 * todo 2-1 將首頁的路由獨立出來
 * */
enum class BookingScreens(val title: String) {
    DateSelection(title = "DateSelection"),
    Booking(title = "BookingRoom"),
    RoomSelection(title = "RoomSelection/{type}"),
    Payment(title = "Payment"),
    BookingSuccess(title = "BookingSuccess"),

}

const val BOOKING_NAVIGATION_ROUTE = "Booking"
const val ROOM_SELECTION_ROUTE = "RoomSelection/{type}" // 使用 {type} 來接收參數


fun NavGraphBuilder.bookingScreenRoute(
    viewModel: BookingViewModel,
    navController: NavHostController
) {
    composable(
        route = DateSelection.name,
    ) {
        DateSelectionScreen(
            navController = navController
        )
    }
    composable(
        route = Booking.name,
    ) {
        BookingScreen(navController = navController)
    }
    composable(
        route = "${RoomSelection.name}/{type}",//字串不能帶參數
    ) { backStackEntry ->
        val typeChar = backStackEntry.arguments?.getString("type")?.getOrNull(0) ?: 'C'
        RoomSelectionScreen(viewModel,navController = navController, type = typeChar)
    }
    composable(
        route = Payment.name,
    ) {
        PaymentScreen(
            navController = navController
        )
    }
    composable(
        route = BookingSuccess.name,
    ) {
        BookingSuccessScreen(navController = navController)
    }
}


//    val HOME_NAVIGATION_ROUTE = "booking"
//
//    fun genHomeNavigationRoute() = HOME_NAVIGATION_ROUTE
//
//    fun NavGraphBuilder.bookingScreenRoute(navController: NavHostController) {
//        composable(
//            route = "BookingScreen",
//        ) {
//            BookingScreen(navController = navController)
//        }
//        composable(
//            route = "DateSelectionScreen",
//        ) {
//            DateSelectionScreen(navController = navController)
//        }
//        composable(
//            route = "PaymentScreen",
//        ) {
//            PaymentScreen(navController = navController)
//        }
//        composable(
//            route = "RoomSelectScreen",
//        ) {
//            RoomSelectScreen(navController = navController, type = "dog")
//        }
//
//
//    }
