package com.example.potel.ui.petsfile

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

interface PetsFileApiService {

    // 获取所有狗的帖子
    @GET("PetsFile/Dogs")
    suspend fun DogsLists(): List<PetsDog>

    // 获取所有猫的帖子
    @GET("PetsFile/Cats")
    suspend fun CatsLists(): List<PetsCat>

    // 添加狗
    @POST("PetsFile/AddDog")
    suspend fun addDog(
        @Body body: AddDogBody
    ): Response<Unit>

    data class AddDogBody(
        val dogOwner: String,
        val dodId: String,
        val dogName: String,
        val dogBreed: String,
        val dogGender: String,
        val dogImage: Int,
    )

    // 添加猫
    @Multipart
    @POST("PetsFile/AddCat")
    suspend fun addCat(
        @Part("catOwner") catOwner: RequestBody,
        @Part("catName") catName: RequestBody,
        @Part("catBreed") catBreed: RequestBody,
        @Part("catGender") catGender: RequestBody,
        @Part catImages: MultipartBody.Part? // 图片作为二进制数据上传
    ): Response<Unit>

    // 更新狗信息
    @PUT("PetsFile/updateDog")
    suspend fun updateDog(
        @Body dogUpdateRequest: DogUpdateRequest
    ): Response<Unit>

    // 更新猫信息
    @PUT("PetsFile/updateCat")
    suspend fun updateCat(
        @Body catUpdateRequest: CatUpdateRequest
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