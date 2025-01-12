package com.example.potel.ui.petsfile


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.potel.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PetFileAddViewModel : ViewModel() {
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

    fun onAddDogClick() {
        // 從 ViewModel 獲取變數
        val ownername: String = _ownerName.value
        val petid: Int = _petId.value
        val petname: String = _petName.value
        val petbreed: String = _petBreed.value
        val petgender: String = _petGender.value
        val petimages: Int = _petImages.value

        // 使用 viewModelScope 發起網路請求
        viewModelScope.launch {
            RetrofitInstance.api.addDog(
                PetsFileApiService.AddDogBody(
                    dogOwner = ownername,
                    dogName = "Max",
                    dogBreed = "123",
                    dogGender = "Male",
                    dogImage = petimages,
                )
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
