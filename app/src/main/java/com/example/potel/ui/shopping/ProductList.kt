package com.example.potel.ui.shopping

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.potel.R
import kotlinx.coroutines.launch

// 定義產品資料類別
data class Products(
    val id: String,
    val name: String,
    val price: Int,
    val image: Int
)

@Composable
fun ProductListScreen(
    navController: NavHostController,
    prdtype: String
) {
    val tag = "ProductListScreen"
    val backStackEntry = navController.getBackStackEntry(ShopScreens.Twoclass.name)
    val ShopViewModel: ShopViewModel = viewModel(backStackEntry, key = "shoppingVM")
    val coroutineScope = rememberCoroutineScope()
    var productList by remember { mutableStateOf<List<Product>>(emptyList()) }
    Log.d(tag, "prdtype=$prdtype")

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            productList = ShopViewModel.getProductList(prdtype)
        }
    }


    ListGrid(
        products = productList,
        onItemClick = {product ->
            // 跳轉到商品詳情頁面，傳遞商品ID
            Log.d(tag, "ListGrid==>prdId=${product.prdId}")
            navController.navigate("${ShopScreens.Information.name}/${product.prdId}")
        }
    )
}


@Composable
fun ListGrid(
    products: List<Product>, // 傳入顯示的產品列表
    onItemClick: (Product) -> Unit // 點擊事件的回傳
) {
    val tag = "ListGrid"
    Log.d(tag, "products=${products.size}")
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // 設定最小欄寬為128dp
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(products.size) { index -> // 遍歷每個產品
            val product = products[index]
            Log.d(tag, "product=$product")
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .padding(12.dp)
                    .border(width = 2.dp, color = Color.Yellow, shape = RoundedCornerShape(10))
                    .background(color = Color.Cyan)
                    .clickable { onItemClick(product) } // 直接呼叫onItemClick
            ) {
                AsyncImage(
                    model = composeImageUrl(product.imageId),
                    contentDescription = "寵物照片",
                    alignment = Alignment.TopCenter,
                    contentScale = ContentScale.FillWidth,
//                    placeholder = painterResource(R.drawable.placeholder)
                )

//                Image(
//                    modifier = Modifier.fillMaxWidth(),
//                    painter = painterResource(id = product.image), // 顯示產品圖片
//                    contentDescription = "Product's image",
//                    contentScale = ContentScale.FillWidth
//                )
                Text(
                    text = product.prdName,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$ ${product.price}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                )
            }
        }
    }
}

// 模擬返回產品列表的函式
//fun fetchProducts(): List<Products> {
//    return listOf(
//        Products("01", "[優格]鮮肉佐餐凍乾\n(雞肉)60公克", 230.0, R.drawable.chickenfro),
//        Products("02", "[優格]鮮肉佐餐凍乾\n(鮭魚)60公克", 230.0, R.drawable.salmonfro),
//        Products("03", "[優格]鮮肉佐餐凍乾\n(羊肉)60公克", 230.0, R.drawable.lambfro),
//        Products("04", "[快吃肉乾]100%-\n涮嘴牛肉條100公克", 250.0, R.drawable.beef),
//        Products("05", "[快吃肉乾]100%-\n雞鮪雙享棒100公克", 250.0, R.drawable.tuna),
//        Products("06", "[快吃肉乾]100%-\n耐嚼玉子燒100公克", 250.0, R.drawable.egg),
////        Products("07", "[P.L.A.Y]胡椒蝴蝶餅\n(啃咬解憂)(狗貓玩具)", 310.0, R.drawable.bitetoy),
////        Products("08", "[P.L.A.Y]班尼迪克蛋\n(啃咬解憂)(狗貓玩具)", 310.0, R.drawable.bitetoy),
//        Products("07", "[Honey Care]超吸收寵物尿片(薰衣草)24入", 490.0, R.drawable.superabsurb),
//        Products("08", "[Honey Care]超消臭寵物尿片(活性碳)24入", 490.0, R.drawable.superstink),
//        Products("09", "[喵樂]小小肉泥條-\n靜岡柴魚(7克18入)", 120.0, R.drawable.meatmud),
//        Products("10", "[喵樂]小小肉泥條-\n南加州雞(7克18入)", 120.0, R.drawable.meatmud),
//        Products("11", "[等等手撕貓零食]\n紓壓大師(貓草)", 160.0, R.drawable.catgrass),
//        Products("12", "[等等手撕貓零食]\n視力大師(牛磺酸)", 160.0, R.drawable.eyesight),
//        Products("13", "[P.L.A.Y]班尼迪克蛋\n(啃咬解憂)(狗貓玩具)", 310.0, R.drawable.bitetoy),
//    )
//}

//@Preview(showBackground = true)
//@Composable
//fun ProductListPreview() {
//    PotelTheme {
//        ListGrid(
//            products = fetchProducts(),
//            onItemClick = { product ->
//                println("Product clicked: ${product.name}")
//            }
//        )
//    }
//}
