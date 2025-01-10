package com.example.potel.ui.booking

import android.util.Log
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
class BookingViewModel : ViewModel() {

    private var tag = "BookingViewModel"
    val apiService = RetrofitInstance.api
    private val _creditCardNumber = MutableStateFlow("")
    val creditCardNumber: StateFlow<String> = _creditCardNumber

//    val _creditCardNumber = MutableStateFlow("")
//    val creditCardNumber = _creditCardNumber.asStateFlow()

    fun onCreditCardNumberChange(text: String) {
        _creditCardNumber.update { text }
    }

    // 定義一個可更改的變數, 但是是私有的(private), 只有VM自己可以改, 外部只能透過提供的method做修改
    private val _addOrderState = MutableStateFlow(Order())
    private val _petEditState = MutableStateFlow(Pet())
    private val _memberEditState = MutableStateFlow(Member())
    private val _roomTypeSelectedState = MutableStateFlow(RoomType())

    val addOrderEditState = _addOrderState.asStateFlow()

    fun setSelectedRoomType(roomType: RoomType) {
        _roomTypeSelectedState.value = roomType
    }

    private var _roomTypesState = MutableStateFlow(emptyList<RoomType>())
    val roomTypesState: StateFlow<List<RoomType>> = _roomTypesState.asStateFlow()
    init {
        // Launch a coroutine in the viewModelScope to call the suspend function
        viewModelScope.launch {
            _roomTypesState.value = fetchRoomTypes()
        }
    }
    private suspend fun fetchRoomTypes(): List<RoomType> {
        try {
            val roomTypes = RetrofitInstance.api.fetchRoomTypes()

            Log.d(tag, "roomTypes: ${roomTypes.size}")
            Log.d(tag, "roomTypes: $roomTypes")
            return roomTypes
        } catch (e: Exception) {
            Log.e(tag, "error: ${e.message}")
            return emptyList()
        }
    }
}

//    // 提供外部讀取目前的值, 但不能直接更動, 因為提出的可能是物件, 這個會讓外部讀出的物件也不能更改內容值
//    val addOrderState = _addOrderState.asStateFlow()
//    val petEditState = _petEditState.asStateFlow()
//    val memberEditState = _memberEditState.asStateFlow()
//    val roomTypeSelectedState = _roomTypeSelectedState.asStateFlow()





    private val _roomTypeList = MutableStateFlow<List<RoomTypeResponse>>(listOf())
    val roomTypeList = _roomTypeList.asStateFlow()

//    fun getRoomAllType() {
//        viewModelScope.launch {
//            val allRoomType = RetrofitInstance.api.getAllRoomType()
//            _roomTypeList.update { allRoomType }
//            Log.d("allRoomType", "size: ${allRoomType.size}")
//
//        }
//
//    }

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
//
//    fun addOrderState(order: Order) {
//        _addOrderState.value = order
//
//    }
//    fun setPrdOrder(prdorder: PrdOrder?){
//        _prdorderEditState.value = prdorder
//    }





