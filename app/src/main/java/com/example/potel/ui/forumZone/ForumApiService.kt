package com.example.potel.ui.forumZone

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ForumApiService {

    @GET("Forum/Posts")
    suspend fun fetchForums(): List<Post>

    @GET("Forum/Likes")
    suspend fun fetchAllLikes(): List<Like>

    @GET("Forum/Comments")
    suspend fun fetchComments(): List<Comment>

    @POST("Forum/AddPost")
    suspend fun addPost(@Body post: Post): Response<ResponseBody>

}

object RetrofitInstance {
    val api: ForumApiService by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/PotelServer/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ForumApiService::class.java)
    }
}