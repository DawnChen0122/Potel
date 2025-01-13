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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
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
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.potel.ui.booking.BookingScreens
import com.example.potel.ui.booking.BookingViewModel
import com.example.potel.ui.booking.bookingScreenRoute
import com.example.potel.ui.carerecords.homeScreenRoute
import com.example.potel.ui.forumZone.ForumScreens
import com.example.potel.ui.forumZone.forumScreenRoute
import com.example.potel.ui.home.Screens
import com.example.potel.ui.home.accountRoute
import com.example.potel.ui.myorders.MyOrdersScreens
import com.example.potel.ui.myorders.myOrdersScreenRoute
import com.example.potel.ui.petsfile.petsfileScreenRoute
import com.example.potel.ui.theme.PotelTheme

import com.example.potel.ui.shopping.shopScreenRoute

import com.example.potel.ui.shopping.ShopScreens
import com.example.potel.ui.shopping.ShopViewModel
import kotlinx.coroutines.launch


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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PotelApp(
    navController: NavHostController = rememberNavController()
) {
    // todo 1-2 先宣告一個完整頁面，包含 BottomBar / NavHost
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry?.destination?.route?.split("/")?.first() ?: "home"
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val currentScreenTitle = findEnumTitleByName(currentScreen,
        MyOrdersScreens::class.java)

    val isForumScreen = currentScreen in ForumScreens.entries.map { it.name }

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .fillMaxSize(),
        topBar = {

            if (!isForumScreen) {
                MainTopAppBar(
                    currentScreen = currentScreen,
                    canNavigateBack = navController.previousBackStackEntry != null,
                    navigateUp = { navController.navigateUp() },
                    scrollBehavior = scrollBehavior
                )
            }
        },
        bottomBar = {
            MainBottomAppBar(navController)
        }
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
    navController: NavHostController = rememberNavController(),
    viewModel: ShopViewModel = viewModel()
) {
    val bookingViewModel : BookingViewModel = viewModel()

    // todo 2-1 這裡是將所有的畫面路徑都列出來
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screens.HomeRoute.name

    ) {
        // todo 2-2 置入所有的畫面路徑
//        homeScreenRoute(navController) // 02 明駿
//        bookingScreenRoute(navController) // 04 芊伃
//        myOrdersScreenRoute(navController) // 27 正能
        shopScreenRoute(viewModel = viewModel, navController) // 07 柏森
//        careRecordsScreenRoute(navController) // 25 泰陽
//        discussZoneScreenRoute(navController) // 16 品伃
//        petsScreenRoute(navController) // 18 勇慶
        accountRoute(navController) //02 明駿
        homeScreenRoute(navController) // 02 明駿
        bookingScreenRoute(viewModel = bookingViewModel,navController) // 04 芊伃
        myOrdersScreenRoute(navController) // 27 正能
        shopScreenRoute(viewModel, navController) // 07 柏森
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainBottomAppBar(navController: NavHostController){
    BottomAppBar(
        // 動作按鈕
        actions = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp), // 增加水平間距
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center // 將內容置中
            ) {
                IconButton(
                    modifier = Modifier
                        .size(60.dp)
                        .weight(0.2f),
                    onClick = {
                        navController.navigate(Screens.HomeRoute.name)
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.home),
                        contentDescription = "Home",
                        modifier = Modifier.size(150.dp)
                    )
                }

                IconButton(
                    modifier = Modifier
                        .size(60.dp)
                        .weight(0.2f),
                    onClick = {
                        navController.navigate(BookingScreens.DateSelection.name)
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
                        .weight(0.2f),
                    onClick = {
                        navController.navigate(ShopScreens.Twoclass.name)
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
                        .weight(0.2f),
                    onClick = {
                        // 先用popbackstack以避免重複載入頁面造成資源損耗, 若沒進入過該頁才改呼叫navigate
                        if(!navController.popBackStack(MyOrdersScreens.MOS01.name, false))
                            navController.navigate(MyOrdersScreens.MOS01.name)
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
                        .weight(0.2f),
                    onClick = {
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.myinfo),
                        contentDescription = "MyInfo",
                        modifier = Modifier.size(150.dp)
                    )
                }
            }
        },
    )
}

fun findEnumTitleByName(name: String, vararg enums: Class<out Enum<*>>): String {
    // 遍历传入的每个枚举类
    for (enumClass in enums) {
        // 获取当前枚举类的所有枚举实例
        val enumConstants = enumClass.enumConstants ?: continue
        for (enumValue in enumConstants) {
            // 检查枚举实例的名称是否匹配
            if (enumValue.name == name) {
                // 使用反射获取 title 属性值
                val titleField = enumClass.getMethod("getTitle") // 调用 getTitle 方法
                return titleField.invoke(enumValue) as String
            }
        }
    }
    // 如果未找到，返回 null
    return ""
}