package com.example.potel.ui.account


import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class OpenpageViewModel : ViewModel() {

    private val _inputError = MutableStateFlow(false)
    val inputError = _inputError.asStateFlow()

    private val _cellphone = MutableStateFlow("")
    val cellphone = _cellphone.asStateFlow()
    val cellphoneRegex = "^[0-9]{10}$".toRegex()

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()
    val emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$".toRegex()

    private val _passwd = MutableStateFlow("")
    val passwd = _passwd.asStateFlow()
    var passwdError by mutableStateOf(false)
    fun passwdchange(passwd: String) {
        val passwdRegex = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,20}$")
        passwdError = !passwd.matches(passwdRegex)
        _passwd.value = passwd
    }


    fun onInputChanged(input: String) {
        viewModelScope.launch {
            delay(500)  // 延遲500毫秒後處理輸入
            if (input.isNotEmpty()) {
                Log.d("PhoneNumberValidation", "Input: $input")  // 記錄輸入的內容
                Log.d("PhoneNumberValidation", "Regex: $cellphoneRegex")  // 記錄電話號碼的正則表達式
                Log.d("PhoneNumberValidation", "Email Regex: $emailRegex")  // 記錄電子郵件的正則表達式

                if (input.contains("@")) {
                    Log.d("PhoneNumberValidation", "輸入看起來是Email")

                    if (input.matches(emailRegex)) {
                        _inputError.value = false
                        _email.value = input
                        Log.d("PhoneNumberValidation", "Email 匹配成功: $input")
                    } else {
                        _inputError.value = true
                        Log.e("PhoneNumberValidation", "Email 匹配失敗: $input")
                    }
                } else {
                    Log.d("PhoneNumberValidation", "輸入看起來是電話號碼")

                    if (input.matches(cellphoneRegex)) {
                        _inputError.value = false
                        _cellphone.value = input
                        Log.d("PhoneNumberValidation", "電話號碼 匹配成功: $input")
                    } else {
                        _inputError.value = true
                        Log.e("PhoneNumberValidation", "電話號碼 匹配失敗: $input")
                    }
                }
            } else {
                _inputError.value = false
                Log.d("PhoneNumberValidation", "輸入為空，清除錯誤狀態")
            }
        }
    }


    private val _login = MutableStateFlow(Change(success = false, message = ""))
    val login = _login.asStateFlow()

    suspend fun login(inputlog: Inputlog): Member {
        val passwd = _passwd.value

        Log.d("Login", "嘗試登入，輸入: $inputlog, 密碼: $passwd")

        // 確保密碼不為空且沒有錯誤
        if (passwd.isNotEmpty() && !passwdError) {
            try {
                Log.d("Login", "準備發送登入請求...")

                // 假設 Retrofit 回傳的是 Response<Member> 類型
                val response = RetrofitInstance.api.login(inputlog.input, inputlog.passwd)

                // 檢查回應是否成功
                if (response.isSuccessful) {
                    val member = response.body()

                    if (member != null) {
                        Log.d("Login", "登入成功，會員資料: $member")
                        return member  // 成功後返回 Member 物件
                    } else {
                        // 如果回應體為 null，提供錯誤訊息
                        Log.e("Login", "返回的 Member 物件為 null")
                        throw Exception("登錄回應的資料為空")
                    }
                } else {
                    // 如果回應失敗，顯示錯誤的 HTTP 狀態碼和訊息
                    Log.e(
                        "Login",
                        "登入失敗，錯誤代碼: ${response.code()}，錯誤訊息: ${response.message()}"
                    )
                    throw Exception("登入失敗，錯誤代碼: ${response.code()}，錯誤訊息: ${response.message()}")
                }

            } catch (e: Exception) {
                // 捕獲任何異常，並在錯誤日誌中顯示詳情
                Log.e("Login", "登入過程中出現錯誤: ${e.message}")
                // 根據需要拋出錯誤或顯示錯誤信息
                throw e
            }
        } else {
            // 如果密碼是空的或者有錯誤，拋出錯誤
            Log.e("Login", "密碼錯誤或未填寫密碼")
            throw Exception("密碼錯誤或未填寫密碼")
        }
    }
}