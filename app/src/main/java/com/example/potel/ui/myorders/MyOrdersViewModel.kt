package com.example.potel.ui.myorders

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class MyOrdersViewModel : ViewModel() {
    private var tag = "MyOrdersViewModel"
    // 定義一個可更改的變數, 但是是私有的(private), 只有VM自己可以改, 外部只能透過提供的method做修改
    private val _orderEditState = MutableStateFlow<Order?>(Order())
    private val _prdorderEditState = MutableStateFlow(PrdOrder())
    private val _petEditState = MutableStateFlow(Pet())
    private val _memberEditState = MutableStateFlow(Member())

    // 提供外部讀取目前的值, 但不能直接更動, 因為提出的可能是物件, 這個會讓外部讀出的物件也不能更改內容值
    val orderEditState = _orderEditState.asStateFlow()
    val prdorderEditState = _prdorderEditState.asStateFlow()
    val petEditState = _petEditState.asStateFlow()
    val memberEditState = _memberEditState.asStateFlow()

    fun setOrder(order: Order?){
        _orderEditState.value = order
    }

    suspend fun getOrders(memberid: Int = 1, orderstate: Char): List<Order> {
        Log.d(tag, "memberid=$memberid, orderstate=$orderstate")
        try {
            val response = RetrofitInstance.api.getOrders(memberid, orderstate)

            Log.d(tag, "response[0].orderid=${response[0].orderid}")

            return response // 回傳List<Order>
        } catch (e: Exception) {
            Log.e(tag, "error: ${e.message}")
            return emptyList()
        }
    }
    suspend fun getOrder(orderid: Int = 0): Order? {
        Log.d(tag, "orderid=$orderid")
        try {
            val response = RetrofitInstance.api.getOrder(orderid)
            return response // 回傳List<Order>
        } catch (e: Exception) {
            Log.e(tag, "error: ${e.message}")
            return null
        }
    }

    suspend fun updateOrder(op:String, order: Order): ResponseObject? {
        Log.d(tag, "orderid=${order.orderid}")
        try {
            val response = RetrofitInstance.api.updateOrder(op, order)
            Log.d(tag, "orderid=${order.orderid}, respcode=${response.respcode}, respmsg=${response.respmsg}")
            return response // 回傳ResponseObject
        } catch (e: Exception) {
            Log.e(tag, "error: ${e.message}")
            return ResponseObject(-1, "更新資料失敗")
        }
    }
}