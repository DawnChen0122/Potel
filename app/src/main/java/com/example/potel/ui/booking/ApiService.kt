package com.example.potel.ui.booking

import com.example.potel.ui.myorders.Order
import com.example.potel.ui.myorders.PrdOrder
import com.example.potel.ui.myorders.ResponseObject
import com.example.potel.ui.myorders.utils.MyCookieJar
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query
import java.sql.Timestamp


data class RoomTypeResponse(
    val roomTypeId:Int,
    val descpt:String,
    val imageId:Int,
    val price:Int,
    val petType:Char,
    val weightL:Int,
    val weightH:Int,
    val status:Char,
    val createDate:Timestamp,
    val modifyDate:Timestamp
)

interface ApiService {

//    @GET("booking/findroomtype")
//    suspend fun getAllRoomType(
//    ): List<RoomTypeResponse>
    @GET("booking/findroomtype")
    suspend fun fetchRoomTypes(): List<RoomType>

//    /** 取得所有該會員的訂房訂單orders */
//    @GET("booking/findroomtype")
//    suspend fun getOrders(
//        @Query("memberid") memberid: Int,
//        @Query("orderstate") orderstate:Char
//    ): List<Order>
//
//    /** 取得指定id的order */
//    @GET("api/order")
//    suspend fun getOrder(
//        @Query("orderid") orderid: Int
//    ): Order
//
//    /** 修改order, 評分, 取消訂單 */
//    @PUT("api/order")
//    suspend fun updateOrder(
//        @Query("op") op: String,
//        @Body updateOrderRequest: Order
//    ): ResponseObject<Any>
//
//
//    /** 取得所有該會員的購物訂單prdorders */
//    @GET("api/prdorders")
//    suspend fun getPrdOrders(
//        @Query("memberid") memberid: Int,
//        @Query("orderstate") orderstate:Char
//    ): ResponseObject<List<PrdOrder>>
//
//    /** 取得指定id的prdorder */
//    @GET("api/prdorder")
//    suspend fun getPrdOrder(
//        @Query("prdordid") prdordid: Int
//    ): ResponseObject<PrdOrder>
//
//    /** 修改prdorder, 取消訂單 */
//    @PUT("api/prdorder")
//    suspend fun updatePrdOrder(
//        @Query("op") op: String,
//        @Body updatePrdOrderRequest: PrdOrder
//    ): ResponseObject<Any>

}

const val baseurl = "http://10.0.2.2:8080/PotelServer/"
// singleton-pattern, 建立一個符合APIService介面的物件
object RetrofitInstance {
    private val okHttpClient = OkHttpClient.Builder()
        .cookieJar(MyCookieJar()) // 設置自定義的 CookieJar
        .build()

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(baseurl) // Base URL
            .client(okHttpClient) // 把自定義的httpclient設置進去使用
            .addConverterFactory(GsonConverterFactory.create()) // GSON for JSON conversion
            .build()
            .create(ApiService::class.java)
    }
}

fun composeImageUrl(imageId: Int): String{
    return "${baseurl}booking/image?imageId=$imageId"
}