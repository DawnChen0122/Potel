//package com.example.potel.ui.shopping
//
//import android.content.Context
//import android.widget.Toast
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Button
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.rememberNavController
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//
//// 顯示 Toast 訊息
//fun showToast(message: String, context: Context) {
//    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
//}
//
//// 根據類別獲取商品列表的 API 請求處理
//fun fetchProductsByCategory(categoryId: Int, context: Context, navController: NavHostController) {
//    CoroutineScope(Dispatchers.IO).launch {
//        try {
//            // 發送 API 請求以根據類別獲取商品
//            val response = RetrofitInstance.api.getProductsByCategory(categoryId)
//            withContext(Dispatchers.Main) {
//                if (response.isNotEmpty()) {
//                    // 成功獲取商品，顯示訊息並導航至商品列表
//                    showToast("商品列表已成功載入", context)
//                    // 根據需要導航
//                    navController.navigate("products_list_route")
//                } else {
//                    showToast("沒有商品可顯示", context)
//                }
//            }
//        } catch (e: Exception) {
//            withContext(Dispatchers.Main) {
//                // 異常處理
//                showToast("無法載入商品：${e.message}", context)
//            }
//        }
//    }
//}
//
// 提交訂單 API 請求處理
//fun submitOrder(orderRequest: OrderRequest, context: Context, navController: NavHostController) {
//    CoroutineScope(Dispatchers.IO).launch {
//        try {
//            // 發送訂單請求
//            val response = RetrofitInstance.api.createOrder(orderRequest)
//            withContext(Dispatchers.Main) {
//                if (response.status == "success") {
//                    // 訂單成功
//                    showToast("訂單已提交，訂單 ID: ${response.orderId}", context)
//                    // 導航到訂單詳情頁面
//                    navController.navigate("order_detail_route/${response.orderId}")
//                } else {
//                    // 訂單失敗
//                    showToast("訂單提交失敗: ${response.status}", context)
//                }
//            }
//        } catch (e: Exception) {
//            withContext(Dispatchers.Main) {
//                // 異常處理
//                showToast("無法提交訂單：${e.message}", context)
//            }
//        }
//    }
//}
//
//// 修改訂單狀態 API 請求處理
//fun updateOrderStatus(orderId: Int, updateOrderRequest: OrderRequest, context: Context) {
//    CoroutineScope(Dispatchers.IO).launch {
//        try {
//            // 發送修改訂單狀態的請求
//            val response = RetrofitInstance.api.updateOrderStatus(orderId, updateOrderRequest)
//            withContext(Dispatchers.Main) {
//                if (response.status == "success") {
//                    showToast("訂單狀態已更新", context)
//                } else {
//                    showToast("無法更新訂單狀態", context)
//                }
//            }
//        } catch (e: Exception) {
//            withContext(Dispatchers.Main) {
//                showToast("無法更新訂單狀態：${e.message}", context)
//            }
//        }
//    }
//}
//
//
//@Composable
//fun ProductListScreen(navController: NavHostController, context: Context) {
//    // 假設你有一個商品類別 ID
//    val categoryId = 1 // 這是示範數據
//
//    // 在畫面加載時觸發 API 請求
//    LaunchedEffect(Unit) {
//        fetchProductsByCategory(categoryId, context, navController)
//    }
//
//    // UI 顯示：商品列表
//    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
//        Text("商品列表", fontSize = 24.sp, fontWeight = FontWeight.Bold)
//        // 顯示商品列表的組件
//        // 在此組件內，你可以展示從 API 獲得的商品資料
//    }
//}
//
//@Composable
//fun OrderScreen(navController: NavHostController, context: Context) {
//    // 假設訂單請求
//    val orderRequest = OrderRequest(productId = 1, quantity = 2, memberId = 123)
//
//    // 按鈕提交訂單
//    Button(
//        onClick = { submitOrder(orderRequest, context, navController) },
//        modifier = Modifier.padding(16.dp)
//    ) {
//        Text("提交訂單")
//    }
//}
//
//
//
//@Composable
//fun AppNavigation() {
//    val navController = rememberNavController()
//
//    NavHost(navController = navController, startDestination = "home") {
//        composable("home") {
//            HomeScreen(navController)
//        }
//        composable("products_list_route") {
//            ProductListScreen(navController, LocalContext.current)
//        }
//        composable("order_detail_route/{orderId}") { backStackEntry ->
//            val orderId = backStackEntry.arguments?.getString("orderId")?.toInt() ?: 0
//            OrderScreen(navController, LocalContext.current)
//        }
//    }
//}
//
//@Composable
//fun HomeScreen(navController: NavHostController) {
//    // 這裡可以展示主畫面的內容，並提供導航到其他畫面
//    Button(onClick = { navController.navigate("products_list_route") }) {
//        Text("查看商品列表")
//    }
//}