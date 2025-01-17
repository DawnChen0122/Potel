package com.example.potel.ui.petsfile


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PetFileAddViewModel : ViewModel() {

    // 使用 MutableStateFlow 和 StateFlow 管理狀態
    private val _ownerName = MutableStateFlow("")
    val ownerName: StateFlow<String> = _ownerName

    private val _petName = MutableStateFlow("")
    val petName: StateFlow<String> = _petName

    private val _petGender = MutableStateFlow("")
    val petGender: StateFlow<String> = _petGender

    private val _contactInfo = MutableStateFlow("")
    val contactInfo: StateFlow<String> = _contactInfo

    private val _petDiscribe = MutableStateFlow("")
    val petDiscribe: StateFlow<String> = _petDiscribe

    private val _petImage = MutableStateFlow("")
    val petImage: StateFlow<String> = _petImage

    // 更新 ownerName
    fun updateOwnerName(newOwnerName: String) {
        _ownerName.value = newOwnerName
    }

    // 更新 petName
    fun updatePetName(newPetName: String) {
        _petName.value = newPetName
    }

    // 更新 petGender
    fun updatePetGender(newPetGender: String) {
        _petGender.value = newPetGender
    }

    // 更新 contactInfo
    fun updateContactInfo(newContactInfo: String) {
        _contactInfo.value = newContactInfo
    }

    // 更新 petDiscribe
    fun updatePetDiscribe(newPetDiscribe: String) {
        _petDiscribe.value = newPetDiscribe
    }

    // 更新 petImage
    fun updatePetImage(newPetImage: String) {
        _petImage.value = newPetImage
    }

    // 處理添加資料的動作
    fun onAddClicked() {
        // 在此處理提交的邏輯，這裡只是示範
        println("資料已提交：$ownerName, $petName, $petGender, $contactInfo, $petDiscribe, $petImage")
    }
}
