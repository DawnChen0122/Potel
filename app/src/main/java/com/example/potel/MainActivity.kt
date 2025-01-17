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
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberBottomAppBarState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.potel.ui.booking.BookingScreens
import com.example.potel.ui.booking.BookingViewModel
import com.example.potel.ui.booking.bookingScreenRoute
import com.example.potel.ui.forumZone.ForumScreens
import com.example.potel.ui.forumZone.forumScreenRoute
import com.example.potel.ui.home.AccountScreens
import com.example.potel.ui.home.accountRoute
import com.example.potel.ui.myorders.MyOrdersScreens
import com.example.potel.ui.myorders.myOrdersScreenRoute
import com.example.potel.ui.petsfile.PetsFileScreens
import com.example.potel.ui.petsfile.petsfileScreenRoute
import com.example.potel.ui.theme.PotelTheme
import com.example.potel.ui.shopping.shopScreenRoute
import com.example.potel.ui.shopping.ShopScreens
import com.example.potel.ui.shopping.ShopViewModel


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

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry?.destination?.route?.split("/")?.first() ?: "home"
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    val currentScreenTitle = findEnumTitleByName(
        currentScreen,
        MyOrdersScreens::class.java
    )

    val isForumScreen = currentScreen in ForumScreens.entries.map { it.name }
    val isAccountScreens = currentScreen in AccountScreens.entries.map { it.name }
        .filterNot { it == AccountScreens.HomeRoute.name }

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

            if (!isAccountScreens) {

                MainBottomAppBar(navController)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {

            TipNavHost(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                navController = navController
            )
        }
    }
}

@Composable
fun TipNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: ShopViewModel = viewModel()
) {
    val bookingViewModel: BookingViewModel = viewModel()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = AccountScreens.Login.name

    ) {


        shopScreenRoute(viewModel = viewModel, navController) // 07 柏森
        accountRoute(navController) //02 明駿
        bookingScreenRoute(navController) // 04 芊伃
        myOrdersScreenRoute(navController) // 27 正能
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
        title = { Text(currentScreen) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
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
fun MainBottomAppBar(navController: NavHostController) {
    BottomAppBar(

        actions = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(
                    modifier = Modifier
                        .size(60.dp)
                        .weight(0.2f),
                    onClick = {
                        navController.navigate(AccountScreens.HomeRoute.name)
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
                        if (!navController.popBackStack(MyOrdersScreens.MOS01.name, false))
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
                        navController.navigate(PetsFileScreens.PetsFileFirst.name)

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
    for (enumClass in enums) {
        val enumConstants = enumClass.enumConstants ?: continue
        for (enumValue in enumConstants) {
            if (enumValue.name == name) {
                val titleField = enumClass.getMethod("getTitle") // 调用 getTitle 方法
                return titleField.invoke(enumValue) as String
            }
        }
    }
    return ""
}