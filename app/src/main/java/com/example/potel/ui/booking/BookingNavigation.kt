package com.example.potel.ui.booking

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.potel.ui.pets.PETS_NAVIGATION_ROUTE
//import com.example.potel.ui.pets.PetsScreenRoute
import com.example.potel.ui.booking.BookingScreens.Booking
import com.example.potel.ui.booking.BookingScreens.DateSelection
import com.example.potel.ui.booking.BookingScreens.RoomSelection
import com.example.potel.ui.booking.BookingScreens.Payment




/**
 * todo 2-1 將首頁的路由獨立出來
 * */
enum class BookingScreens(title: String){
    DateSelection(title = "DateSelection11"),
    Booking(title = "BookingRoom11"),
    RoomSelection(title = "RoomSelection11"),
    Payment(title = "Payment11"),
    BookingSuccess(title = "BookingSuccess11"),


}

fun NavGraphBuilder.PetsScreenRoute(navController: NavHostController) {
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
        route = RoomSelection.name,
    ) {
        RoomSelectScreen(navController = navController, type = "dog")
    }
    composable(
        route = Payment.name,
    ) {
        PaymentScreen(navController = navController)
    }
    composable(
        route = BookingScreens.BookingSuccess.name,
    ) {
        BookingSuccessScreen(navController = navController)
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
}