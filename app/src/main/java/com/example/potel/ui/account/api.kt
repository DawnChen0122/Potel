package com.example.potel.ui.account


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

interface ApiService {
    /** 取得所有該會員的訂房訂單orders */
    @GET("api/orders")
    suspend fun getOrders(@Query("memberid") memberid: Int, @Query("orderstate") orderstate:Char): List<Order>

    /** 取得指定id的order */
    @GET("api/order")
    suspend fun getOrder(@Query("orderid") orderid: Int): Order

    /** 取得所有該會員的購物訂單orders */
    @GET("api/prdorders")
    suspend fun getPrdOrders(@Query("memberid") memberid: Int): List<PrdOrder>

    /** 取得指定id的prdorder */
    @GET("api/prdorder")
    suspend fun getPrdOrder(@Query("prdordid") prdordid: Int): PrdOrder

    /** 修改order, 評分 */
    @PUT("api/order")
    suspend fun updateUser(
        @Query("orderid") orderid: Int,
        @Body updateOrderRequest: Order
    ): Order?


//    /** 修改user */
//    @PUT("/api/users/{id}")
//    suspend fun updateUser(
//        @Path("id") id: Int,
//        @Body updateUserRequest: UpdateUserRequest
//    ): UpdateUserResponse?
}

const val baseurl = "http://10.0.2.2:8080/PotelServer/"
// singleton-pattern, 建立一個符合APIService介面的物件
object RetrofitInstance {
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(baseurl) // Base URL
            .addConverterFactory(GsonConverterFactory.create()) // GSON for JSON conversion
            .build()
            .create(ApiService::class.java)
    }
}

fun composeImageUrl(imageid: Int): String{
    return "${baseurl}api/image?imageid=$imageid"
}