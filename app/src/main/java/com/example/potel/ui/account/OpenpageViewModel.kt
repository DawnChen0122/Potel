package com.example.potel.ui.account


import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class OpenpageViewModel : ViewModel() {

    private val _inputError = MutableStateFlow(false)
    val inputError: StateFlow<Boolean> = _inputError

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _cellphone = MutableStateFlow("")
    val cellphone: StateFlow<String> = _cellphone

    val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
    val cellphoneRegex = Regex("^[0-9]{10}$")


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
            delay(500)
            validateInput(input)
        }
    }

    private fun validateInput(input: String) {
        if (input.isNotEmpty()) {
            if (input.contains("@")) {
                validateEmail(input)
            } else {
                validatePhoneNumber(input)
            }
        } else {
            _inputError.value = false
        }
    }

    private fun validateEmail(input: String) {
        if (input.matches(emailRegex)) {
            _inputError.value = false
            _email.value = input
        } else {
            _inputError.value = true
        }
    }

    private fun validatePhoneNumber(input: String) {
        if (input.matches(cellphoneRegex)) {
            _inputError.value = false
            _cellphone.value = input
        } else {
            _inputError.value = true
        }
    }

    private val _login = MutableStateFlow(Change(success = false, message = ""))
    val login = _login.asStateFlow()

    suspend fun login(inputlog: Inputlog): Member {
        val passwd = _passwd.value

        Log.d("Login", "嘗試登入，輸入: $inputlog, 密碼: $passwd")


        if (passwd.isNotEmpty() && !passwdError) {
            try {
                Log.d("Login", "準備發送登入請求...")

                val response = RetrofitInstance.api.login(inputlog.input, inputlog.passwd)

                if (response.isSuccessful) {
                    val member = response.body()

                    if (member != null) {
                        Log.d("Login", "登入成功，會員資料: $member")
                        return member
                    } else {

                        Log.e("Login", "返回的 Member 物件為 null")
                        throw Exception("登錄回應的資料為空")
                    }
                } else {

                    Log.e(
                        "Login",
                        "登入失敗，錯誤代碼: ${response.code()}，錯誤訊息: ${response.message()}"
                    )
                    throw Exception("登入失敗，錯誤代碼: ${response.code()}，錯誤訊息: ${response.message()}")
                }

            } catch (e: Exception) {
                Log.e("Login", "登入過程中出現錯誤: ${e.message}")
                throw e
            }
        } else {

            Log.e("Login", "密碼錯誤或未填寫密碼")
            throw Exception("密碼錯誤或未填寫密碼")
        }
    }
}