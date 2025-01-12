package com.example.potel.ui.booking

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.potel.ui.forumZone.PostUpdateRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.days


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
    //把days傳到信用卡頁面
        private val _daySelectState = MutableStateFlow(0) // 初始化值
        val daySelectState = _daySelectState.asStateFlow()

        fun setDay(days: Int) {
            _daySelectState.value = days // 更新值
        }





    // 定義一個可更改的變數, 但是是私有的(private), 只有VM自己可以改, 外部只能透過提供的method做修改
    private val _addOrderState = MutableStateFlow(newOrder())
    val addOrderEditState = _addOrderState.asStateFlow()

    private val _roomTypeSelectedState = MutableStateFlow(RoomType())
     val roomTypeSelectedState = _roomTypeSelectedState.asStateFlow()

    fun setSelectedRoomType(roomType: RoomType) {
        _roomTypeSelectedState.value = roomType
    }

    private var _roomTypesState = MutableStateFlow(emptyList<RoomType>())
    val roomTypesState: StateFlow<List<RoomType>> = _roomTypesState.asStateFlow()
    init {
        // Launch a coroutine in the viewModelScope to call the suspend function
        viewModelScope.launch {
            val roomTypes = fetchRoomTypes()
            _roomTypesState.update { roomTypes }
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

    fun addOrder(newOrder: newOrder) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.addOrder(newOrder)
                if (response.isSuccessful) {
                    Log.d(tag, "addOrder successfully: ${response.body()}")
                } else {
                    Log.e(tag, "Error addOrder: Code ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e(tag, "Error addOrder: ${e.message}")
            }
        }
    }




}






