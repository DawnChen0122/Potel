//package com.example.potel.ui.account
//
//import android.content.Context
//import android.util.Log
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import androidx.navigation.NavController
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.launch
//
//class AccountViewModel1 : ViewModel() {
//
//    private val _email1 = MutableStateFlow("")
//    val email1 = _email1.asStateFlow()
//
//    private val _password1 = MutableStateFlow("")
//    val password1 = _password1.asStateFlow()
//
//    var uidError1 by mutableStateOf(false)
//    var passwordError1 by mutableStateOf(false)
//    var emailError1 by mutableStateOf(false)
//
//    private val _loading = MutableStateFlow(false)
//    val loading = _loading.asStateFlow()
//
//    // 登入方法
//    fun login(context: Context, navController: NavController) {
//        val account1 = _email1.value
//        val password1 = _password1.value
//
//        // 確保表單欄位正確
//        if (account1.isEmpty() || password1.isEmpty() || uidError1 || passwordError1 || emailError1) {
//            // 如果有錯誤，直接返回
//            return
//        }
//
//        // 開始請求
//        viewModelScope.launch {
//            _loading.value = true  // 顯示 loading
//
//            try {
//                // 使用 Retrofit 發送登入請求
//                val response = RetrofitInstance.api.login(account1, password1)
//
//                _loading.value = false  // 隱藏 loading
//
//                if (response.success) {
//                    // 登入成功，儲存 token 或其他用戶資料
//                    val token = response.token
//                    val sharedPreferences =
//                        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
//                    sharedPreferences.edit().putString("token", token).apply()
//
//                    // 跳轉到主頁
//                    navController.navigate(R.id.action_loginFragment_to_homeFragment)
//                } else {
//                    // 登入失敗，顯示錯誤訊息（例如帳號或密碼錯誤）
//                    Log.e("LoginError", "Login failed: ${response.errorMessage}")
//                }
//            } catch (e: Exception) {
//                _loading.value = false  // 隱藏 loading
//
//                // 處理錯誤，顯示錯誤訊息（例如網絡錯誤）
//                Log.e("LoginError", "Login request failed: ${e.message}")
//            }
//        }
//    }
//}
