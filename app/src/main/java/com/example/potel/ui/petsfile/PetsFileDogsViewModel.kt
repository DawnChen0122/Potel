package com.example.potel.ui.petsfile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


/**
 * todo 2-3 宣告 HomeViewModel
 * 討論：
 * (1) 為什麼要 ViewModel
 * (2) ViewModel 要做什麼事情，有哪些資料應該放到 ViewModel
 * */
class PetsFileDogsViewModel : ViewModel() {
    // MutableStateFlow用來監控指定資料狀態，當資料一改變即可通知對應畫面更新
    // MutableStateFlow常與ViewModel搭配，可以讓UI元件在生命週期期間作出適當更新
    private val _dogsState = MutableStateFlow(emptyList<PetsDog>())

    // 將_booksState轉成唯讀狀態的booksState給外部使用，目的在於不讓外部直接透過booksState改原始資料
    val dogsState: StateFlow<List<PetsDog>> = _dogsState.asStateFlow()

    // 一開始就呼叫fetchBooks()取得測試資料以更新_bookState內容
    init {
        viewModelScope.launch {
            val pets = fetchPets()
            _dogsState.update { pets }
        }
    }

    /** 新增一本書到List並更新_bookState內容 */
    fun addDog(item: PetsDog) {
        _dogsState.update {
            val dogsList = it.toMutableList()
            dogsList.add(item)
            dogsList
        }
    }

    /** 移除一本書並更新_bookState內容 */
    fun removeIDog(item: PetsDog) {
        _dogsState.update {
            val dogsList = it.toMutableList()
            dogsList.remove(item)
            dogsList
        }
    }


    /**
     * 載入測試需要資料
     * @return 多本書資訊
     */
    private suspend fun fetchPets(): List<PetsDog> {
        return RetrofitInstance.api.DogsLists()
//        return listOf(
//            PetsDog("Buddy", "Golden Retriever", "Male", R.drawable.dog1),
//            PetsDog("Bella", "Bulldog", "Female", R.drawable.dog2),
//            PetsDog("Charlie", "Labrador Retriever", "Male", R.drawable.dog3),
//            PetsDog("Daisy", "Poodle", "Female", R.drawable.dog4),
//            PetsDog("Max", "Beagle", "Male", R.drawable.dog5),
//            PetsDog("Lucy", "German Shepherd", "Female", R.drawable.dog6),
//            PetsDog("Rocky", "Boxer", "Male", R.drawable.dog7),
//            PetsDog("Molly", "Rottweiler", "Female", R.drawable.dog8),
//            PetsDog("Oscar", "Dachshund", "Male", R.drawable.dog9),
//            PetsDog("Luna", "Chihuahua", "Female", R.drawable.dog10),
//            PetsDog("Sadie", "Yorkshire Terrier", "Female", R.drawable.dog11),
//            PetsDog("Bailey", "Shih Tzu", "Male", R.drawable.dog12),
//            PetsDog("Cooper", "Doberman Pinscher", "Male", R.drawable.dog13),
//            PetsDog("Zoe", "Cocker Spaniel", "Female", R.drawable.dog14),
//            PetsDog("Toby", "Maltese", "Male", R.drawable.dog15)
//        )
    }
}
