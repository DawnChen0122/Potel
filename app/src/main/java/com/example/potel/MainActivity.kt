package com.example.potel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.potel.ui.booking.bookingScreenRoute
import com.example.potel.ui.discussZone.discussZoneScreenRoute
import com.example.potel.ui.myorders.myOrdersScreenRoute
import com.example.potel.ui.theme.PotelTheme
import com.example.potel.ui.home.HOME_NAVIGATION_ROUTE
import com.example.potel.ui.home.homeScreenRoute

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PotelTheme {
                PotelApp()
            }
        }
    }
}


@Composable
fun PotelApp(
    navController: NavHostController = rememberNavController()
) {
    // todo 1-2 先宣告一個完整頁面，包含 BottomBar / NavHost
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            // todo 1-3 將 NavHost 放在 Scaffold Content 裡
            TipNavHost(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                navController = navController
            )

        }


    }
}

// todo 1-6 將 NavHost 放在一個獨立的 Composable 裡
@Composable
fun TipNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    // todo 2-1 這裡是將所有的畫面路徑都列出來
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = HOME_NAVIGATION_ROUTE
    ) {
        // todo 2-2 置入所有的畫面路徑
        homeScreenRoute(navController) // 02 明駿
        bookingScreenRoute(navController) // 04 芊伃
        myOrdersScreenRoute(navController) // 27 正能
//        shoppingScreenRoute(navController) // 07 柏森
//        careRecordsScreenRoute(navController) // 25 泰陽
         discussZoneScreenRoute(navController) // 16 品伃
//        petsScreenRoute(navController) // 18 勇慶
    }

}


