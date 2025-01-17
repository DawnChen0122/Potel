package com.example.potel.ui.petsfile

import androidx.lifecycle.ViewModel
import com.example.potel.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PetsFileCatsViewModel : ViewModel() {
    // MutableStateFlow用來監控指定資料狀態，當資料一改變即可通知對應畫面更新
    // MutableStateFlow常與ViewModel搭配，可以讓UI元件在生命週期期間作出適當更新
    private val _catsState = MutableStateFlow(emptyList<PetsCat>())
    // 將_booksState轉成唯讀狀態的booksState給外部使用，目的在於不讓外部直接透過booksState改原始資料
    val catsState: StateFlow<List<PetsCat>> = _catsState.asStateFlow()

    // 一開始就呼叫fetchBooks()取得測試資料以更新_bookState內容
    init {
        _catsState.update { fetchPets() }
    }

    /** 新增一本書到List並更新_bookState內容 */
    fun addItem(item: PetsCat) {
        _catsState.update {
            val catsList = it.toMutableList()
            catsList.add(item)
            catsList
        }
    }

    /** 移除一本書並更新_bookState內容 */
    fun removeItem(item: PetsCat) {
        _catsState.update {
            val catsList = it.toMutableList()
            catsList.remove(item)
            catsList
        }
    }


    /**
     * 載入測試需要資料
     * @return 多本書資訊
     */
    private fun fetchPets(): List<PetsCat> {
        return listOf(
            PetsCat("Whiskers", "Persian", "Male", R.drawable.dog1),
            PetsCat("Lily", "Siamese", "Female", R.drawable.dog2),
            PetsCat("Simba", "Maine Coon", "Male", R.drawable.dog3),
            PetsCat("Bella", "Bengal", "Female", R.drawable.dog4),
            PetsCat("Leo", "British Shorthair", "Male", R.drawable.dog5),
            PetsCat("Mia", "Ragdoll", "Female", R.drawable.dog6),
            PetsCat("Oliver", "Abyssinian", "Male", R.drawable.dog7),
            PetsCat("Chloe", "Scottish Fold", "Female", R.drawable.dog8),
            PetsCat("Max", "Burmese", "Male", R.drawable.dog9),
            PetsCat("Nala", "Norwegian Forest", "Female", R.drawable.dog10),
            PetsCat("Oscar", "Sphynx", "Male", R.drawable.dog11),
            PetsCat("Daisy", "Birman", "Female", R.drawable.dog12),
            PetsCat("Tiger", "Oriental Shorthair", "Male", R.drawable.dog13),
            PetsCat("Zoe", "Turkish Angora", "Female", R.drawable.dog14),
            PetsCat("Toby", "Exotic Shorthair", "Male", R.drawable.dog15)
        )
    }

}