package com.example.potel.ui.shopping

import androidx.lifecycle.ViewModel
import com.example.potel.ui.myorders.Product
import com.example.potel.ui.myorders.RetrofitInstance


class ShopViewModel : ViewModel() {

    suspend fun getProductList(prdtype: String.Companion = String): List<Product> {
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