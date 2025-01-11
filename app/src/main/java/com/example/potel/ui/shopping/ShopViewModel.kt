package com.example.potel.ui.shopping

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ShopViewModel : ViewModel() {

    private val _cardnumber = MutableStateFlow("")

    val cardnumber = _cardnumber.asStateFlow()

    var cardnumberError by mutableStateOf(false)
    fun onCardnumberChanged(cardnumber: String) {
        val cardnumberRegex = Regex("^[0-9]{16}$")
        cardnumberError = !cardnumber.matches(cardnumberRegex)
        _cardnumber.value = cardnumber
    }

    suspend fun getProductList(prdtype: String): List<Product> {
//        Log.d(tag, "memberid=$memberid, orderstate=$orderstate")
        try {
            val response = RetrofitInstance.api.getProductList(prdtype)

//            Log.d(tag, "response[0].prdtype=${response[0].prdtype}")

            return response
        } catch (e: Exception) {
//            Log.e(tag, "error: ${e.message}")
            return emptyList()
        }
    }

    suspend fun getProduct(prdId: Int): Product? {
        val tag = "getProduct"
        Log.d(tag, "prdId=$prdId")
        try {
            val response = RetrofitInstance.api.getProduct(prdId)
            return response
        } catch (e: Exception) {
//            Log.e(tag, "error: ${e.message}")
            return null
        }
    }

    suspend fun addorder(prdId: Int): Product? {
        val tag = "getProduct"
        Log.d(tag, "prdId=$prdId")
        try {
            val response = RetrofitInstance.api.getProduct(prdId)
            return response
        } catch (e: Exception) {
//            Log.e(tag, "error: ${e.message}")
            return null
        }
    }
}








