
package com.example.potel.ui.booking


import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.potel.ui.booking.BookingScreens.Booking
import com.example.potel.ui.booking.BookingScreens.BookingSuccess
import com.example.potel.ui.booking.BookingScreens.DateSelection
import com.example.potel.ui.booking.BookingScreens.Payment
import com.example.potel.ui.booking.BookingScreens.RoomSelection

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
    fun NavGraphBuilder.bookingScreenRoute(
        viewModel: BookingViewModel,
        navController: NavHostController
    ) {

        composable(

            route = DateSelection.name,
        ) {
            DateSelectionScreen(
                bookingVM = viewModel,
                navController = navController
            )

        }
        composable(
            route = Booking.name,
        ) {
            BookingScreen(
                bookingVM = viewModel, navController = navController
            )
        }
        composable(
            route = "${RoomSelection.name}/{type}",//字串不能帶參數
        ) { backStackEntry ->
            val typeChar = backStackEntry.arguments?.getString("type")?.getOrNull(0) ?: 'C'
            RoomSelectionScreen(viewModel, navController = navController, type = typeChar)
        }
        composable(
            route = Payment.name,
        ) {
            PaymentScreen(
                bookingVM = viewModel,
                navController = navController
            )
        }
        composable(
            route = BookingSuccess.name,
        ) {
            BookingSuccessScreen(
                bookingVM = viewModel,
                navController = navController
            )
        }
    }

