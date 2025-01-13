package com.example.potel.ui.petsfile


data class DogUpdateRequest(
    val dogId: Int,
    val dogOwner: String,
    val dogName: String,
    val dogBreed: String,
    val dogGender: String,
    val dogImages: Int?

)
data class CatUpdateRequest(
    val catId: Int,
    val catOwner: String,
    val catName: String,
    val catBreed: String,
    val catGender: String,
    val catImages: Int?
)
data class Post(
    val postId: Int=0,
    val memberId: Int = 0,
    val title: String = "",
    val content: String = "",
    val createDate: String = "",
    val modifyDate: String? = null,
    val imageId: Int? = null
)
data class NewPost(
    val memberId: Int = 0,
    val title: String = "",
    val content: String = ""
)