package com.example.potel.ui.account

import android.util.Log
import com.example.potel.ui.myorders.utils.MyCookieJar
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query


interface ApiService {

    @GET("member/reset")
    suspend fun checkEmailAndCellphone(
        @Query("EMAIL") email: String,
        @Query("CELLPHONE") cellphone: String
    ): Check


    @PUT("member/reset")
    suspend fun ChangePassWord(
        @Body member: Member
    ): Check



    @GET("member/login")
    suspend fun login(
        @Query("INPUT") input: String,
        @Query("PASSWORD") password: String
    ): Member





    @POST("member/add")
    suspend fun addmember(
        @Body member: Member
    ): Check
}



const val baseurl = "http://10.0.2.2:8080/PotelServer/"
// singleton-pattern, 建立一個符合APIService介面的物件


object RetrofitInstance {
    private val okHttpClient = OkHttpClient.Builder()
        .cookieJar(MyCookieJar()) // 設置自定義的 CookieJar
        .addInterceptor { chain ->
            // 捕獲並打印 URL
            val request = chain.request()
            val url = request.url.toString()  // 獲取發出的 URL
            Log.d("Request URL", "發出的 URL: $url")  // 打印 URL

            // 繼續執行請求
            chain.proceed(request)
        }
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

