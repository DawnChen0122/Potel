package com.example.potel.ui.forumZone

import com.example.potel.ui.myorders.utils.MyCookieJar
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ForumApiService {

    @GET("Forum/Posts")
    suspend fun fetchForums(): List<Post>

    @GET("Forum/Likes")
    suspend fun fetchAllLikes(): List<Like>

    @GET("Forum/Comments")
    suspend fun fetchComments(): List<Comment>

    @Multipart
    @POST("Forum/AddPost")
    suspend fun addPost(
        @Part("memberId") memberId: RequestBody,
        @Part("title") title: RequestBody,
        @Part("content") content: RequestBody,
        @Part image: MultipartBody.Part? // 圖片作為二進制數據上傳
    ): Response<Unit>

    @POST("Forum/AddComment")
    suspend fun addComment(@Body newComment: NewComment): Response<Comment>

    @DELETE("Forum/posts/{postId}")
    suspend fun deletePost(@Path("postId") postId: Int): Response<Unit>

    @DELETE("Forum/comments/{commentId}")
    suspend fun deleteComment(@Path("commentId") commentId: Int): Response<Unit>


    @Multipart
    @PUT("Forum/updatePostWithImage")
    suspend fun updatePostWithImage(
        @Part("postId") postId: RequestBody,
        @Part("title") title: RequestBody,
        @Part("content") content: RequestBody,
        @Part image: MultipartBody.Part?
    ): Response<Unit>

    @PUT("Forum/updatePost")
    suspend fun updatePost(
        @Body postUpdateRequest: PostUpdateRequest
    ): Response<Unit>

    @PUT("Forum/updateComment")
    suspend fun updateComment(
        @Body commentUpdateRequest: CommentUpdateRequest
    ): Response<Unit>
}

const val baseurl = "http://10.0.2.2:8080/PotelServer/"
object RetrofitInstance {
    private val okHttpClient = OkHttpClient.Builder()
        .cookieJar(MyCookieJar()) // 設置自定義的 CookieJar
        .build()

    val api: ForumApiService by lazy {
        Retrofit.Builder()
            .baseUrl(baseurl) // Base URL
            .client(okHttpClient) // 把自定義的httpclient設置進去使用
            .addConverterFactory(GsonConverterFactory.create()) // GSON for JSON conversion
            .build()
            .create(ForumApiService::class.java)
    }
}
fun composeImageUrl(imageid: Int): String{
    return "${baseurl}api/image?imageid=$imageid"
}