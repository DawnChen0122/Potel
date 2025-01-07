package com.example.potel.ui.shopping

import androidx.lifecycle.ViewModel


class ShopViewModel : ViewModel() {

    suspend fun getProductList(prdtype: String): List<Product> {
//        Log.d(tag, "memberid=$memberid, orderstate=$orderstate")
        try {
            val response = RetrofitInstance.api.getProductList(prdtype)

//            Log.d(tag, "response[0].prdtype=${response[0].prdtype}")

            return response // 回傳List<Order>
        } catch (e: Exception) {
//            Log.e(tag, "error: ${e.message}")
            return emptyList()
        }
    }
}