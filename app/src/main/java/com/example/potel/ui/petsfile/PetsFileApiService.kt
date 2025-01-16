package com.example.potel.ui.petsfile

import com.example.potel.ui.forumZone.PostUpdateRequest
import okhttp3.MultipartBody
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

interface PetsFileApiService {

    // 获取所有狗的帖子
    @GET("PetsFile/Dogs")
    suspend fun DogsLists(): List<PetsDog>

    // 获取所有猫的帖子
    @GET("PetsFile/Cats")
    suspend fun CatsLists(): List<PetsCat>

    // 添加狗
    @Multipart
    @POST("PetsFile/AddDog")
    suspend fun addDog(
        @Part("dogOwner") dogOwner: RequestBody,
        @Part("dogName") dogName: RequestBody,
        @Part("dogBreed") dogBreed: RequestBody,
        @Part("dogGender") dogGender: RequestBody,
        @Part image: MultipartBody.Part?
    ): Response<Unit>

    @Multipart
    @PUT("PetsFile/UpdatePostWithImage")
    suspend fun updatePostWithImage(
        @Part("postId") postId: RequestBody,
        @Part("title") title: RequestBody,
        @Part("content") content: RequestBody,
        @Part image: MultipartBody.Part?
    ): Response<Unit>

    @Multipart
    @POST("PetsFile/AddPost")
    suspend fun addPost(
        @Part("memberId") memberId: RequestBody,
        @Part("title") title: RequestBody,
        @Part("content") content: RequestBody,
        @Part image: MultipartBody.Part? // 圖片作為二進制數據上傳
    ): Response<Unit>

    @Multipart
    @POST("PetsFile/AddCat")
    suspend fun addCat(
        @Part("catOwner") catOwner: RequestBody,
        @Part("catName") catName: RequestBody,
        @Part("catBreed") catBreed: RequestBody,
        @Part("catGender") catGender: RequestBody,
        @Part image: MultipartBody.Part?
    ): Response<Unit>

    // 更新狗信息
    @PUT("PetsFile/updateDog")
    suspend fun updateDog(
        @Body dogUpdateRequest: PetsDog
    ): Response<Unit>

    // 更新猫信息
    @PUT("PetsFile/updateCat")
    suspend fun updateCat(
        @Body catUpdateRequest: PetsCat
    ): Response<Unit>

    // 删除狗
    @DELETE("PetsFile/deleteDog/{dogId}")
    suspend fun deleteDog(@Path("dogId") dogId: Int): Response<Unit>

    // 删除猫
    @DELETE("PetsFile/deleteCat/{catId}")
    suspend fun deleteCat(@Path("catId") catId: Int): Response<Unit>

}


const val baseurl = "http://10.0.2.2:8080/PotelServer/"

object RetrofitInstance {

    val api: PetsFileApiService by lazy {
        Retrofit.Builder()
            .baseUrl(baseurl) // Base URL
            .addConverterFactory(GsonConverterFactory.create()) // GSON for JSON conversion
            .build()
            .create(PetsFileApiService::class.java)
    }
}

fun composeImageUrl(imageid: Int): String {
    return "${baseurl}api/image?imageid=$imageid"
}