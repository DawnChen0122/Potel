package com.example.potel.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

/**
 * todo 2-2 將首頁的畫面獨立出來
 * 將每個頁面拆分成三個區塊
 * (1) HomeRoute 使用 ViewModel 的資料，此為 NavigationController 導向的起始點
 * (2) HomeScreen 實際畫面，將參數抽出來，方便 Preview
 * (3) PreviewHomeScreen 預覽畫面（不透過 ViewModel 預覽畫面）
 * */

@Composable
fun HomeRoute(
//    homeViewModel: HomeViewModel = viewModel(),
    navController: NavHostController
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)
    ) {
        Text(
            text = "首頁",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Blue
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "入住須知",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Blue
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "顯示房客評價",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Blue
        )

        Spacer(modifier = Modifier.height(30.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(30.dp)
            ) {

                Text(
                    text = "房型介紹",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Blue
                )
            }

        Spacer(modifier = Modifier.height(30.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(30.dp)
        ) {
            Text(
                text = "聊天室",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Blue
            )

            Spacer(modifier = Modifier.width(30.dp))

            Text(
                text = "毛小孩追蹤",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Blue
            )

        }
    }
}


//    val items by homeViewModel.items.collectAsState()

//    HomeScreen(
//        items = items,
//        onDetailClick = { title -> navController.navigate(genDetailNavigationRoute(title)) },
//        onGetApiClick = homeViewModel::getApiData,
//    )

//@Preview
//@Composable
//fun PreviewHomeScreen() {
//    HomeScreen()
//}

//    @Composable
//    fun HomeScreen(
//    items: List<TipHomeItemUiState> = listOf(),
//    onGetApiClick: () -> Unit = {},
//    onDetailClick: (String) -> Unit = {}
//    ) {
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
//    }
//}


@Preview(showBackground = true)
@Composable
fun DefaultPreview10() {
    HomeRoute(navController = rememberNavController())
}