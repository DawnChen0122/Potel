package com.example.potel.ui.petsfile

import com.example.potel.R

class PetsCat(
    var name: String = "",
    var breed: String = "",
    var gender: String = "",
    var image: Int = R.drawable.dog // 宠物图片（可以是资源ID）
){
        override fun equals(other: Any?): Boolean {
            return this.name == (other as PetsCat).name // 判断是否是同一只宠物，可以根据名字来比较
        }

        override fun hashCode(): Int {
            return name.hashCode() // 使用宠物名字的hashCode作为对象的hash值
        }
    }
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


