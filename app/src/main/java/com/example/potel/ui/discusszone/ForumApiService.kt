package com.example.potel.ui.discusszone

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ForumApiService {


}
object RetrofitInstance {
    val api: ForumApiService by lazy {
        Retrofit.Builder()
            .baseUrl("http://localhost:8080/PotelServer") // Base URL
            .addConverterFactory(GsonConverterFactory.create()) // GSON for JSON conversion
            .build()
            .create(ForumApiService::class.java)
    }
}