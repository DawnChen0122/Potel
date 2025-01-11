package com.example.potel.ui.account



import android.R.id.input
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

    private val _phonenumber = MutableStateFlow("")
    val phonenumber = _phonenumber.asStateFlow()
    val phonenumberRegex = "^[0-9]{10}$".toRegex()

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
            delay(500)
            if (input.isNotEmpty()) {
                if (input.contains("@")) {
                    if (input.matches(emailRegex)) {
                        _inputError.value = false
                        _email.value = input
                    } else {
                        _inputError.value = true
                    }
                } else {
                    if (input.matches(phonenumberRegex)) {
                        _inputError.value = false
                        _phonenumber.value = input
                    } else {
                        _inputError.value = true
                    }
                }
            } else {
                _inputError.value = false
            }
        }
    }


    private val _login = MutableStateFlow(Change(success = false, message = ""))
    val login = _login.asStateFlow()

    suspend fun login(input: Input): Member {
        val password = _password.value

        Log.d("Login", "嘗試登入，輸入: ${input.toString()}, 密碼: $password")

        if (password.isNotEmpty() && !passwordError) {
            try {
                Log.d("Login", "登入成功")
                // 這裡假設 Retrofit 回傳的是 Member 類型的物件
                val response = RetrofitInstance.api.login(input.email, input.passwd)

                // 假設 response 是 Member 類型，可以直接返回
                return response // 直接返回成功的 Member 物件
            } catch (e: Exception) {
                e.printStackTrace() // 打印錯誤堆疊，幫助調試
                Log.d("Login", "登入失敗: ${e.toString()}") // 記錄登入失敗

                // 登入失敗時，返回一個 Member 物件，表示失敗，並可以填充錯誤訊息
                return Member(
                    memberid = 0,
                    name = "",
                    password = input.passwd,
                    phonenumber = "",
                    address = "",
                    email = input.email // 可以選擇儲存錯誤時的 email
                )
            }
        } else {
            Log.d("Login", "登入失敗")

            // 如果密碼錯誤，仍然返回一個 Member 物件，並保持原始值
            return Member(
                memberid = 0,
                name = "",
                password = input.passwd,
                phonenumber = "",
                address = "",
                email = input.email // 同樣儲存錯誤時的 email
            )
        }
    }
}