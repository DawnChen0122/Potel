package com.example.potel.ui.account

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST


fun getRetrofitInstance(): Retrofit {
    return Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080/PotelServer/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}


data class LoginRequest(val email: String, val password: String)
data class LoginResponse(val token: String, val message: String)

interface ApiService {
    @POST("login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    companion object
}


object RetrofitInstance {
    val api: ApiService by lazy {
        getRetrofitInstance().create(ApiService::class.java)
    }
}


data class LoginRequest2(val phone: String, val password: String)
data class LoginResponse2(val token: String, val message: String)

interface ApiService2 {
    @POST("login")
    suspend fun login(@Body request: LoginRequest2): LoginResponse2
}


object RetrofitInstance2 {
    val api: ApiService2 by lazy {
        getRetrofitInstance().create(ApiService2::class.java)
    }
}
