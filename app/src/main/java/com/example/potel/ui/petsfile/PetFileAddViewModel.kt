package com.example.potel.ui.petsfile


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.potel.R
import com.example.potel.ui.forumZone.NewPost
import com.example.potel.ui.forumZone.Post
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

class PetFileAddViewModel : ViewModel() {
    private val tag = "tag_PetFileAddViewModel"
    private val _ownerName = MutableStateFlow("")
    val ownerName: StateFlow<String> = _ownerName

    private val _petId = MutableStateFlow(0)  // 改為 Int 類型
    val petId: StateFlow<Int> = _petId

    private val _petName = MutableStateFlow("")
    val petName: StateFlow<String> = _petName

    private val _petBreed = MutableStateFlow("")
    val petBreed: StateFlow<String> = _petBreed

    private val _petGender = MutableStateFlow("")
    val petGender: StateFlow<String> = _petGender

    private val _petImages = MutableStateFlow(0)  // 改為 Int 類型
    val petImages: StateFlow<Int> = _petImages

    // 更新狀態
    fun updateOwnerName(name: String) {
        _ownerName.value = name
    }

    fun updatePetId(id: Int) {
        _petId.value = id
    }

    fun updatePetName(name: String) {
        _petName.value = name
    }

    fun updatePetBreed(breed: String) {
        _petBreed.value = breed
    }

    fun updatePetGender(gender: String) {
        _petGender.value = gender
    }

    fun updatePetImages(images: Int) {
        _petImages.value = images
    }

    fun addPost(post: NewPost, imagePart: MultipartBody.Part?) {
        viewModelScope.launch {
            try {
                val memberIdPart =
                    post.memberId.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                val titlePart = post.title.toRequestBody("text/plain".toMediaTypeOrNull())
                val contentPart = post.content.toRequestBody("text/plain".toMediaTypeOrNull())

                val response = RetrofitInstance.api.addPost(
                    memberId = memberIdPart,
                    title = titlePart,
                    content = contentPart,
                    image = imagePart
                )

                if (response.isSuccessful) {
                    Log.d(tag, "Post added successfully: ${response.body()}")
                } else {
                    Log.e(tag, "Error adding post: Code ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e(tag, "Error adding post: ${e.message}")
            }
        }
    }

    fun updatePostWithImage(post: Post, imagePart: MultipartBody.Part?) {
        viewModelScope.launch {
            try {
                val postIdPart =
                    post.postId.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                val titlePart = post.title.toRequestBody("text/plain".toMediaTypeOrNull())
                val contentPart = post.content.toRequestBody("text/plain".toMediaTypeOrNull())

                val response = RetrofitInstance.api.updatePostWithImage(
                    postId = postIdPart,
                    title = titlePart,
                    content = contentPart,
                    image = imagePart
                )

                if (response.isSuccessful) {
                    Log.d(tag, "updatePostWithImage successfully: ${response.body()}")
//                    petsFileData()
                } else {
                    Log.e(tag, "Error updatePostWithImage Code ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e(tag, "Error updatePostWithImage: ${e.message}")
            }
        }
    }

    fun onAddDogClick(imagePart: MultipartBody.Part?) {
        Log.d("onAddDogClick", imagePart.toString())

        // 從 ViewModel 獲取變數
        val ownername = _ownerName.value.toRequestBody("text/plain".toMediaTypeOrNull())
        val petname = _petName.value.toRequestBody("text/plain".toMediaTypeOrNull())
        val petbreed = _petBreed.value.toRequestBody("text/plain".toMediaTypeOrNull())
        val doggender = _petGender.value.toRequestBody("text/plain".toMediaTypeOrNull())

        // 使用 viewModelScope 發起網路請求
        viewModelScope.launch {
            RetrofitInstance.api.addDog(
                dogOwner = ownername,
                dogBreed = petbreed,
                dogName = petname,
                dogGender = doggender,
                image = imagePart
            )
        }
    }

    // 提交 Cat 資料
    fun onAddCatClick() {
        // 從 ViewModel 獲取變數
        val ownername: String = _ownerName.value
        val petid: Int = _petId.value
        val petname: String = _petName.value
        val petbreed: String = _petBreed.value
        val petgender: String = _petGender.value
        val petimages: Int = _petImages.value

        // 使用 viewModelScope 發起網路請求
        viewModelScope.launch {
            RetrofitInstance.api.addCat(
                PetsFileApiService.AddCatBody(
                    catOwner = ownername,
                    catName = "Max",
                    catBreed = "123",
                    catGender = "Male",
                    catImage = petimages
                )
            )
        }
    }

    fun onMaleClick() {
        _petGender.update { "Male" }
    }

    fun onFemaleClick() {
        _petGender.update { "Female" }

    }
    // 處理添加資料的動作
//    fun onAddCatClick() {
//        // 在此處理提交的邏輯，這裡只是示範
//        println("資料已提交：$ownerName, $petName, $petGender, $contactInfo, $petDiscribe, $petImage")
//    }
//
//    fun onAddDogClick() {
//        val name = _petName.value
//        val image: Int = R.drawable.dog
//        val owner: String = _ownerName.value
//        viewModelScope.launch {
//            RetrofitInstance.api.addDog(
//                PetsFileApiService.AddDogBody(
//                    dogOwner = owner,
//                    dogId = "3",
//                    dogName = "Max",
//                    dogBreed = "123",
//                    dogGender = "Male",
//                    dogImage = "5"
//                )
//            )
//        }
//    }
}
