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
import retrofit2.Response

interface ApiService {

    @GET("member/reset")
    suspend fun checkEmailAndCellphone(
        @Query("EMAIL") email: String,
        @Query("CELLPHONE") cellphone: String
    ): Check


    @PUT("member/reset")
    suspend fun changepasswd(
        @Body member: InputRequest
    ): Check


    @GET("member/login")
    suspend fun login(
        @Query("INPUT") input: String,
        @Query("PASSWD") passwd: String
    ): Response<Member>


    @POST("member/add")
    suspend fun addmember(
        @Body member: Member
    ): Check


    @POST("member/edit")
    suspend fun edit(
        @Body member: Edit
    ): Check

}

const val baseurl = "http://10.0.2.2:8080/PotelServer/"

object RetrofitInstance {
    private val okHttpClient = OkHttpClient.Builder()
        .cookieJar(MyCookieJar())
        .addInterceptor { chain ->
            val request = chain.request()
            val url = request.url.toString()
            Log.d("Request URL", "發出的 URL: $url")

            chain.proceed(request)
        }
        .build()

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(baseurl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}

