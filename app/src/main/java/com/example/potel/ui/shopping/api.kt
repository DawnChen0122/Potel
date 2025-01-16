package com.example.potel.ui.shopping

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

// 定義 API 服務介面
interface ApiService {

    /** 取得所有商品類別 */
    @GET("shopping/List")
    suspend fun getProductList(
        @Query("prdtype") prdtype: String
    ): List<Product>


    /** 取得指定商品詳細資訊 */
    @GET("shopping/Product")
    suspend fun getProduct(
        @Query("prdId") prdId: Int
    ): Product


    /** 提交訂單 */
    @POST("shopping/Order")
    suspend fun addOrder(
        @Body orderRequest: OrderRequest
    ): Response

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


// 定義訂單請求資料模型
data class OrderRequest(
    val prdId: Int, // 商品 ID
    val prdCount: Int, // 訂購數量
    val memberId: Int, // 會員 ID
    val amount: Int, //總價
    val status: String, //狀態
    val prdorderid: Int //訂單 ID
)


// 定義訂單回應資料模型
data class Response(
    val rc: Int, // 訂單 ID
    val status: String // 訂單狀態
)
