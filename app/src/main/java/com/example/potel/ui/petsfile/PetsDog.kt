package com.example.potel.ui.petsfile

import com.example.potel.R

class PetsDog(
    var name: String = "",
    var breed: String = "",
    var gender: String = "",
    var image: Int = R.drawable.dog // 宠物图片（可以是资源ID）
) {
    override fun equals(other: Any?): Boolean {
        return this.name == (other as PetsDog).name // 判断是否是同一只宠物，可以根据名字来比较
    }

    override fun hashCode(): Int {
        return name.hashCode() // 使用宠物名字的hashCode作为对象的hash值
    }
}
