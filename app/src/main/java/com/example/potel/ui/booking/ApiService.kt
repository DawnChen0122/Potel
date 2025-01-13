package com.example.potel.ui.booking

import com.example.potel.ui.myorders.utils.MyCookieJar
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface ApiService {

    @GET("booking/findroomtype")
    suspend fun fetchRoomTypes(): List<RoomType>

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