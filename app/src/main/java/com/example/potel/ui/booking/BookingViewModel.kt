package com.example.potel.ui.booking

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update


/**
 * todo 2-3 宣告 HomeViewModel
 * 討論：
 * (1) 為什麼要 ViewModel
 * (2) ViewModel 要做什麼事情，有哪些資料應該放到 ViewModel
 * */
class BookingViewModel : ViewModel() {

    var checkInDate: String? = null
    var checkOutDate: String? = null
    var petType: String? = null
    var roomType: String? = null
    var totalAmount: Double = 0.0

    fun submitBooking() {
        viewModelScope.launch {
            // 在此處發送資料到後端 API
            // 使用 Retrofit 發送 POST 請求
        }
    }

    private val _paymentInfo :MutableStateFlow<String> = MutableStateFlow("")

//    private val _items = MutableStateFlow<List<TipHomeItemUiState>>(listOf())
//    val items = _items.asStateFlow()

    fun addPaymentInfo(info:String){
        _paymentInfo.update { info }
    }


    fun getApiData() {
//        // todo 2-5 取得 API 資料，目前先用假資料
//        _items.update {
//            listOf(
//                TipHomeItemUiState(
//                    title = "Home",
//                    imageVector = Icons.Filled.Home
//                ),
//                TipHomeItemUiState(
//                    title = "Search",
//                    imageVector = Icons.Filled.Search
//                ),
//                TipHomeItemUiState(
//                    title = "Delete",
//                    imageVector = Icons.Filled.Delete
//                ),
//            )
//        }
    }


}