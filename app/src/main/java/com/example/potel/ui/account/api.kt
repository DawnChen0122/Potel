package com.example.potel.ui.account

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


//fun getRetrofitInstance(): Retrofit {
//    return Retrofit.Builder()
//        .baseUrl("http://10.0.2.2:8080/PotelServer/")
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//}


//data class LoginRequest(val email: String, val password: String)
//data class LoginResponse(val token: String, val message: String)

interface ApiService {
    @GET("member/login")
    suspend fun login(
        @Query("loginid") loginid: String,
        @Query("password") password: String
    ): String

}

//
//interface ApiService2 {
//    @POST("login")
//    suspend fun login(@Query("loginid") loginid: String, @Query("password") password: String): LoginResponse
//}

//object RetrofitInstance {
//    val api: ApiService by lazy {
//        getRetrofitInstance().create(ApiService::class.java)
//    }
//}


//data class LoginRequest2(val phone: String, val password: String)
//data class LoginResponse2(val token: String, val message: String)
const val baseurl = "http://10.0.2.2:8080/PotelServer/"
// singleton-pattern, 建立一個符合APIService介面的物件
object RetrofitInstance {
//    private val okHttpClient = OkHttpClient.Builder()
//        .cookieJar(MyCookieJar()) // 設置自定義的 CookieJar
//        .build()

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(baseurl) // Base URL
//            .client(okHttpClient) // 把自定義的httpclient設置進去使用
            .addConverterFactory(GsonConverterFactory.create()) // GSON for JSON conversion
            .build()
            .create(ApiService::class.java)
    }
}

