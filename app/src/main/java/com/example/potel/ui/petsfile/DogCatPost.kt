package com.example.potel.ui.petsfile

data class DogUpdateRequest(
    val dogId: Int,
    val dogOwner: String,
    val dogName: String,
    val dogBreed: String,
    val dogGender: String,
    val dogImages: Int? // 可选图片ID
)

data class CatUpdateRequest(
    val catId: Int,
    val catOwner: String,
    val catName: String,
    val catBreed: String,
    val catGender: String,
    val catImages: Int? // 可选图片ID
)
