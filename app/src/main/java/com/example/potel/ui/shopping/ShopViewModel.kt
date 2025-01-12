package com.example.potel.ui.shopping

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.common.api.Status
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ShopViewModel : ViewModel() {

    private val _cardnumber = MutableStateFlow("")
    val cardnumber = _cardnumber.asStateFlow()

    private val _expiredatenumber = MutableStateFlow("")
    val expiredatenumber = _expiredatenumber.asStateFlow()

    private val _safecodenumber = MutableStateFlow("")
    val safecodenumber = _safecodenumber.asStateFlow()

    private val _productList = MutableStateFlow<List<Product>>(listOf())
    val productList = _productList.asStateFlow()

    private val _product = MutableStateFlow<Product?>(null)
    val product = _product.asStateFlow()

    private val _productCount = MutableStateFlow(1)
    val productCount = _productCount.asStateFlow()

    private val _status = MutableStateFlow("")
    val status = _status.asStateFlow()

    private val _prdorderid = MutableStateFlow(1)
    val productorderId = _prdorderid.asStateFlow()

    private val _navRequest = MutableStateFlow<String?>(null)
    val navRequest = _navRequest.asStateFlow()

    private val _completeOrderId = MutableStateFlow<Int?>(null)
    val completeOrderId = _completeOrderId.asStateFlow()


    var cardnumberError by mutableStateOf(false)
    fun onCardnumberChanged(cardnumber: String) {
        val cardnumberRegex = Regex("^[0-9]{16}$")
        cardnumberError = !cardnumber.matches(cardnumberRegex)
        _cardnumber.value = cardnumber
    }
    var expiredatenumberError by mutableStateOf(false)
    fun onExpiredatenumberChanged(expiredatenumber: String) {
        val expiredatenumberRegex = Regex("^[0-9]{4}$")
        expiredatenumberError = !expiredatenumber.matches(expiredatenumberRegex)
        _expiredatenumber.value = expiredatenumber
    }
    var safecodenumberError by mutableStateOf(false)
    fun onSafecodenumberChanged(safecodenumber: String) {
        val safecodenumberRegex = Regex("^[0-9]{3}$")
        safecodenumberError = !safecodenumber.matches(safecodenumberRegex)
        _safecodenumber.value = safecodenumber
    }

    fun initProductList(prdType: String) {
        viewModelScope.launch {
            val productList = fetchProductList(prdType)
            _productList.update { productList }
        }
    }

    private suspend fun fetchProductList(prdtype: String): List<Product> {
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

    fun getProduct(prdId: Int) {
        viewModelScope.launch {
            val product = fetchProduct(prdId)
            _product.update { product }
        }
    }

    private suspend fun fetchProduct(prdId: Int): Product? {
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

    fun onSubmitClick() {
        viewModelScope.launch {
            val addOrderResponse = addorder()
            Log.d("completeOrder", "addOrderResponse: $addOrderResponse")
            if ((addOrderResponse ?: 0) > 0) {
                _completeOrderId.update { addOrderResponse }
                _navRequest.update { ShopScreens.Ordercheck.name }
            }
        }
    }

    suspend fun addorder(): Int? {
        Log.d("shopViewModel", "addOrder: ${_product.value?.prdName} ${_product.value?.prdId}")
        val productId = _product.value?.prdId ?: return null
        val productCount = _productCount.value
        val memberId = 0
        val amount = productCount * (_product.value?.price ?: 0)


        Log.d("shopViewModel", "pid: $productId")
        Log.d("shopViewModel", "productCount: $productCount")
        Log.d("shopViewModel", "memberId: $memberId")
        Log.d("shopViewModel", "amount: $amount")
        Log.d("shopViewModel", "status: $status")
        Log.d("shopViewModel", "prdorderid: $productorderId")

        try {
            val response = RetrofitInstance.api.addOrder(
                OrderRequest(
                    prdId = productId,
                    prdCount = productCount,
                    memberId = memberId,
                    amount = amount,
                    status = status.value,
                    prdorderid = productorderId.value,
                    )
            )
            return response.rc ?: 0
        } catch (e: Exception) {
//            Log.e(tag, "error: ${e.message}")
            return null
        }
    }

    fun onCountChanged(count: Int) {
        _productCount.update { count }
    }
}








