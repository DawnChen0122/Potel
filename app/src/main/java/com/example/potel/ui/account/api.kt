//import okhttp3.MultipartBody
//import okhttp3.RequestBody
//import retrofit2.Response
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import retrofit2.http.GET
//import retrofit2.http.Multipart
//import retrofit2.http.POST
//import retrofit2.http.Part
//import retrofit2.http.Path
//
//// 測試網站說明：https://reqres.in/
//interface ApiService {
//    /** 登入 */
//    @GET("rest/user/{uid}/{password}")
//    suspend fun login(@Path("uid") uid: String, @Path("password") password: String): Response<Unit>
//
//    /** 註冊新user */
//    @Multipart
//    @POST("rest/user")
//    suspend fun register(
//        // @Part("key") for text form fields
//        @Part("uid") uid: RequestBody,
//        @Part("password") password: RequestBody,
//        // MultipartBody.Part for files
//        @Part image: MultipartBody.Part?
//    ): Response<Unit>
//
//    /** 取得所有書資訊 */
//    @GET("rest/book/all")
////    suspend fun fetchBooks(): Response<String>
//    suspend fun fetchBooks(): List<Book>
//}
//
//object RetrofitInstance {
//    val api: ApiService by lazy {
//        Retrofit.Builder()
//            .baseUrl("http://10.0.2.2:8080/BookshopRestDBDemo_Server/") // Base URL
//            .addConverterFactory(GsonConverterFactory.create()) // GSON for JSON conversion
//            .build()
//            .create(ApiService::class.java)
//    }
//}