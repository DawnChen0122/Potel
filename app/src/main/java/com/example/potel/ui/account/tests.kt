package com.example.potel.ui.account

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


data class LoginResponse(
    val success: Boolean,
    val token: String,
    val errorMessage: String?
)

class AccountViewModel : ViewModel() {

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    var uidError by mutableStateOf(false)
    var passwordError by mutableStateOf(false)
    var emailError by mutableStateOf(false)

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    // 登入方法
    fun login(context: Context, navController: NavController) {
        val account = _email.value
        val password = _password.value

        // 確保表單欄位正確
        if (account.isEmpty() || password.isEmpty() || uidError || passwordError || emailError) {
            // 如果有錯誤，直接返回
            return
        }

        // 開始請求
        viewModelScope.launch {
            _loading.value = true  // 顯示 loading

            try {
                // 假設發送登入請求的 API 方法
                val response = RetrofitInstance.api.login(loginid = account, password = password)

                _loading.value = false  // 隱藏 loading

                if (response.success) {
                    // 登入成功，儲存 token 或其他用戶資料
                    val token = response.token
                    val sharedPreferences =
                        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                    sharedPreferences.edit().putString("token", token).apply()

                    // 跳轉到主頁
                    navController.navigate(R.id.action_loginFragment_to_homeFragment)
                } else {
                    // 登入失敗，顯示錯誤訊息（例如帳號或密碼錯誤）
                    Log.e("LoginError", "Login failed: ${response.errorMessage}")
                }
            } catch (e: Exception) {
                _loading.value = false  // 隱藏 loading

                // 處理錯誤，顯示錯誤訊息（例如網絡錯誤）
                Log.e("LoginError", "Login request failed: ${e.message}")
            }
        }
    }
}
