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

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()
    var passwordError by mutableStateOf(false)
    fun onPasswordChanged(password: String) {
        val passwordRegex = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,20}$")
        passwordError = !password.matches(passwordRegex)
        _password.value = password
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

    suspend fun login(inputRequest: InputRequest): Member {
        val password = _password.value

        Log.d("Login", "嘗試登入，輸入: $inputRequest, 密碼: $password")

        if (password.isNotEmpty() && !passwordError) {
            try {
                Log.d("Login", "登入成功")
                // 這裡假設 Retrofit 回傳的是 Member 類型的物件
                val response = RetrofitInstance.api.login(inputRequest.input, inputRequest.passwd)

                // 假設 response 是 Member 類型，可以直接返回
                return response // 直接返回成功的 Member 物件
            } catch (e: Exception) {
                e.printStackTrace() // 打印錯誤堆疊，幫助調試
                Log.d("Login", "登入失敗: ${e.toString()}") // 記錄登入失敗

                // 登入失敗時，返回一個 Member 物件，表示失敗，並可以填充錯誤訊息
                return Member(
                    memberid = 0,
                    name = "",
                    passwd = inputRequest.passwd,
                    cellphone = "",
                    address = "",
                    gender = "",
                    email = "",
                    birthday = "",
                )
            }
        } else {
            Log.d("Login", "登入失敗")

            // 如果密碼錯誤，仍然返回一個 Member 物件，並保持原始值
            return Member(
                memberid = 0,
                name = "",
                passwd = inputRequest.passwd,
                cellphone = "",
                address = "",
                gender = "",
                email = "",
                birthday = "",
            )
        }
    }
}