package com.example.potel.ui.booking

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


/**
 * todo 2-3 宣告 HomeViewModel
 * 討論：
 * (1) 為什麼要 ViewModel
 * (2) ViewModel 要做什麼事情，有哪些資料應該放到 ViewModel
 * */
class BookingViewModel : ViewModel() {

    private var tag = "BookingViewModel"

    // 定義一個可更改的變數, 但是是私有的(private), 只有VM自己可以改, 外部只能透過提供的method做修改
    private val _addOrderState = MutableStateFlow(
        Order(
            amount = 100
        )
    )
    private val _petEditState = MutableStateFlow(Pet())
    private val _memberEditState = MutableStateFlow(Member())
    private val _roomtypeEditState = MutableStateFlow(RoomType())


    // 提供外部讀取目前的值, 但不能直接更動, 因為提出的可能是物件, 這個會讓外部讀出的物件也不能更改內容值
    val addOrderState = _addOrderState.asStateFlow()
    val petEditState = _petEditState.asStateFlow()
    val memberEditState = _memberEditState.asStateFlow()
    val roomtypeEditState = _roomtypeEditState.asStateFlow()

    val _creditCardNumber = MutableStateFlow("")
    val creditCardNumber = _creditCardNumber.asStateFlow()

    fun onCreditCardNumberChange(text: String) {
        _creditCardNumber.update { text }
    }

    fun getRoomAllType() {
        ApiService
    }

//    fun submitBooking() {
//        viewModelScope.launch {
//            // 在此處發送資料到後端 API
//            // 使用 Retrofit 發送 POST 請求
//        }
//    }


    private val _paymentInfo: MutableStateFlow<String> = MutableStateFlow("")

//    private val _items = MutableStateFlow<List<TipHomeItemUiState>>(listOf())
//    val items = _items.asStateFlow()

    fun addPaymentInfo(info: String) {
        _paymentInfo.update { info }
    }

    fun addOrderState(order: Order) {
        _addOrderState.value = order

    }
//    fun setPrdOrder(prdorder: PrdOrder?){
//        _prdorderEditState.value = prdorder
//    }


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