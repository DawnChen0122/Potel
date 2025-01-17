package com.example.potel.ui.petsfile


data class CatUpdateRequest(
    val catId: Int,
    val catOwner: String,
    val catName: String,
    val catBreed: String,
    val catGender: String,
    val catImages: Int?
)
