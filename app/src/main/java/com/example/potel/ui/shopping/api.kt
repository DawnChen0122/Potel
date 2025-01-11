package com.example.potel.ui.shopping

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

// 定義 API 服務介面
interface ApiService {

    /** 取得所有商品類別 */
    @GET("shopping/List")
    suspend fun getProductList(
        @Query("prdtype") prdtype: String
    ): List<Product>

//    /** 根據類別獲取商品列表 */
//    @GET("shopping/Information")
//    suspend fun getProductInformation(@Query("categoryId") categoryId: Int): List<Product>

    /** 取得指定商品詳細資訊 */
    @GET("shopping/Product")
    suspend fun getProduct(
        @Query("prdId") prdId: Int
    ): Product

    /** 提交訂單 */
    @POST("shopping/Order")
    suspend fun addOrder(
        @Body orderRequest: OrderRequest
    ): Int


//    /** 修改訂單狀態（例如，付款、發貨等） */
//    @PUT("api/order")
//    suspend fun updateOrderStatus(
//        @Query("orderId") orderId: Int,
//        @Body updateOrderRequest: OrderRequest
//    ): OrderResponse
}

val baseurl = "http://10.0.2.2:8080/PotelServer/"
// 基本的 API 基礎 URL
object RetrofitInstance {
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(baseurl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}


// 根據圖片 ID 生成圖片 URL
fun composeImageUrl(imageId: Int): String {
    return "${baseurl}shopping/image?imageid=$imageId" // 根據圖片 ID 組成圖片 URL
}

// 定義商品類別資料模型
data class Category(
    val id: Int, // 類別 ID
    val name: String // 類別名稱
)

// 定義商品資料模型
data class Product(
    val prdId: Int, // 商品 ID
    val prdName: String, // 商品名稱
    val price: Int, // 商品價格
    val imageId: Int, // 商品圖片 ID
    val prdDesc: String //商品描述
)


//// 定義商品詳細資料模型
//data class ProductDetail(
//    val id: Int, // 商品 ID
//    val name: String, // 商品名稱
//    val description: String, // 商品描述
//    val price: Int, // 商品價格
//    val stock: Int // 商品庫存量
//)

// 定義訂單請求資料模型
data class OrderRequest(
    val prdId: Int, // 商品 ID
    val prdCount: Int, // 訂購數量
    val memberId: Int, // 會員 ID
    val amount: Int //總價
)

// 定義訂單回應資料模型
//data class OrderResponse(
//    val orderId: Int, // 訂單 ID
//    val status: String // 訂單狀態
//)
