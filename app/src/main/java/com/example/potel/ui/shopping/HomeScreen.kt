package com.example.potel.ui.shopping

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

/**
 * todo 2-2 將首頁的畫面獨立出來
 * 將每個頁面拆分成三個區塊
 * (1) HomeRoute 使用 ViewModel 的資料，此為 NavigationController 導向的起始點
 * (2) HomeScreen 實際畫面，將參數抽出來，方便 Preview
 * (3) PreviewHomeScreen 預覽畫面（不透過 ViewModel 預覽畫面）
 * */

@Composable
fun HomeRoute(
    homeViewModel: HomeViewModel = viewModel(),
    navController: NavHostController
) {
//    val items by homeViewModel.items.collectAsState()

//    HomeScreen(
//        items = items,
//        onDetailClick = { title -> navController.navigate(genDetailNavigationRoute(title)) },
//        onGetApiClick = homeViewModel::getApiData,
//    )
}

@Preview
@Composable
fun PreviewHomeScreen() {
    HomeScreen()
}

@Composable
fun HomeScreen(
//    items: List<TipHomeItemUiState> = listOf(),
//    onGetApiClick: () -> Unit = {},
//    onDetailClick: (String) -> Unit = {}
) {
//    Column {
//        Text(
//            modifier = Modifier
//                .background(TipColor.Pink80)
//                .padding(12.dp)
//                .clickable(onClick = onGetApiClick),
//            text = "取得資料"
//        )
//
//        items.forEach { item ->
//            TipHomeItem(
//                modifier = Modifier.clickable(onClick = { onDetailClick.invoke(item.title) }),
//                uiState = item
//            )
//        }
//
//    }
}