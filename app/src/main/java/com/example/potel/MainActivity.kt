package com.example.potel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.potel.ui.booking.BookingScreens
import com.example.potel.ui.booking.BookingScreens.Booking
import com.example.potel.ui.booking.PetsScreenRoute
import com.example.potel.ui.forumZone.ForumScreens
import com.example.potel.ui.forumZone.forumScreenRoute
import com.example.potel.ui.home.AccountScreens
import com.example.potel.ui.myorders.myOrdersScreenRoute
import com.example.potel.ui.theme.PotelTheme
import com.example.potel.ui.home.accountRoute
import com.example.potel.ui.myorders.MyOrdersScreens
import com.example.potel.ui.petsfile.PetsFileScreens
import com.example.potel.ui.petsfile.petsfileScreenRoute
import com.example.potel.ui.shopping.ShopScreens
import com.example.potel.ui.shopping.shopScreenRoute



import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

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

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PotelApp(
    navController: NavHostController = rememberNavController()
) {

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry?.destination?.route?.split("/")?.first() ?: "home"
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    val isForumScreen = currentScreen in ForumScreens.entries.map { it.name }

    val currentScreenTitle = findEnumTitleByName(currentScreen,
        AccountScreens::class, MyOrdersScreens::class, BookingScreens::class,
        ForumScreens::class, PetsFileScreens::class, ShopScreens::class
    )





    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
            .fillMaxSize()
            .fillMaxWidth(),
        topBar = {

            if (!isForumScreen) {
                MainTopAppBar(
                    currentScreen = currentScreenTitle,
                    canNavigateBack = navController.previousBackStackEntry != null,
                    navigateUp = { navController.navigateUp() },
                    scrollBehavior = scrollBehavior
                )
            }
        },
        bottomBar = {
            MainBottomAppBar(navController = navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            TipNavHost(
                modifier = Modifier
                    .fillMaxWidth(),
                navController = navController
            )
        }
    }
}

// todo 1-6 將 NavHost 放在一個獨立的 Composable 裡
@Composable
fun TipNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    // todo 2-1 這裡是將所有的畫面路徑都列出來
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = AccountScreens.HomeRoute.name

    ) {
        // todo 2-2 置入所有的畫面路徑
        accountRoute(navController) //02 明駿
        PetsScreenRoute(navController)// 04 芊伃
        myOrdersScreenRoute(navController) // 27 正能
        shopScreenRoute(navController) // 07 柏森
        forumScreenRoute(navController) // 16 品伃
        petsfileScreenRoute(navController) // 18 勇慶
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(
    currentScreen: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior
) {
    TopAppBar(
        // 設定頁面標題
        title = { Text(currentScreen) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            // 如果可回前頁，就顯示Back按鈕
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior
    )
}



fun findEnumTitleByName(name: String, vararg enums: KClass<out Enum<*>>): String {
    for (enumClass in enums) {
        enumClass.java.enumConstants?.firstOrNull { it.name == name }?.let { enumValue ->
            return enumValue::class.memberProperties
                .firstOrNull { it.name == "title" }
                ?.getter?.call(enumValue) as? String ?: ""
        }
    }

    return ""
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable



fun MainBottomAppBar(navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp), // 這裡給Row一點邊距
        horizontalArrangement = Arrangement.SpaceBetween, // 按鈕平均分佈
        verticalAlignment = Alignment.CenterVertically // 垂直居中
    )

    {

        IconButton(
            modifier = Modifier
                .size(60.dp)
                .width(10.dp),
            onClick = {
                navController.navigate(route = Booking.name)
            },
        ) {
            Icon(
                painter = painterResource(R.drawable.booking),
                contentDescription = "Booking",
                modifier = Modifier.size(150.dp)
            )
        }


        IconButton(
            modifier = Modifier
                .size(60.dp)
                .width(10.dp),
            onClick = {
                navController.navigate(route = ShopScreens.Twoclass.name)
            },
        ) {
            Icon(
                painter = painterResource(R.drawable.shopping),
                contentDescription = "Shopping",
                modifier = Modifier.size(150.dp)
            )
        }


        IconButton(
            modifier = Modifier
                .size(60.dp)
                .width(10.dp),
            onClick = { navController.navigate(route = MyOrdersScreens.MOS01.name)
                // 先用popbackstack以避免重複載入頁面造成資源損耗, 若沒進入過該頁才改呼叫navigate

//                if (!navController.popBackStack(AccountScreens.HomeRoute.name, false))
//                    navController.navigate(AccountScreens.HomeRoute.name)
            }
        ) {
            Icon(
                painter = painterResource(R.drawable.myorders),
                contentDescription = "MyOrders",
                modifier = Modifier.size(150.dp)
            )
        }


        IconButton(
            modifier = Modifier
                .size(60.dp)
                .width(10.dp),
            onClick = {
                navController.navigate(route = PetsFileScreens.PetsFileFirst.name)
            }
        ) {
            Icon(
                painter = painterResource(R.drawable.myinfo),
                contentDescription = "MyInfo",
                modifier = Modifier.size(150.dp)
            )
        }
    }
}