package com.example.potel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.potel.ui.booking.bookingScreenRoute
import com.example.potel.ui.discussZone.discussZoneScreenRoute
import com.example.potel.ui.myorders.myOrdersScreenRoute
import com.example.potel.ui.theme.PotelTheme
import com.example.potel.ui.home.HOME_NAVIGATION_ROUTE
import com.example.potel.ui.home.homeScreenRoute
import com.example.potel.ui.myorders.Screens
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

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
            .fillMaxSize(),
        topBar = {
            MainTopAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            MainBottomAppBar()
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
    navController: NavHostController = rememberNavController()
) {
    // todo 2-1 這裡是將所有的畫面路徑都列出來
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screens.MOS0501.name
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
fun MainBottomAppBar(){
    BottomAppBar(
        // 動作按鈕
        actions = {
            IconButton(
                onClick = {
//                    scope.launch {
//                        snackbarHostState.showSnackbar(
//                            "BottomAppBar - Check",
//                            withDismissAction = true
//                        )
//                    }
                }
            ) {
                Icon(Icons.Filled.Check, contentDescription = "check")
            }

            IconButton(
                onClick = {
//                scope.launch {
//                    snackbarHostState.showSnackbar(
//                        "BottomAppBar - Edit",
//                        withDismissAction = true
//                    )
//                }
                }
            ) {
                Icon(
                    Icons.Filled.Edit,
                    contentDescription = "edit",
                )
            }

            IconButton(
                onClick = {
//                    scope.launch {
//                        snackbarHostState.showSnackbar(
//                            "BottomAppBar - Delete",
//                            withDismissAction = true
//                        )
//                    }
                }
            ) {
                Icon(
                    Icons.Filled.Delete,
                    contentDescription = "delete",
                )
            }
        },
        // BottomAppBar也可放FloatingActionButton
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
//                    scope.launch {
//                        snackbarHostState.showSnackbar(
//                            "BottomAppBar - Add",
//                            withDismissAction = true
//                        )
//                    }
                },
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Icon(Icons.Filled.Add, "Localized description")
            }
        }
    )
}