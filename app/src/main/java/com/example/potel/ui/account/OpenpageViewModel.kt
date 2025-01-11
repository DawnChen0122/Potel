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

    suspend fun login(member: Member): Check {
        val password = _password.value

        if (password.isNotEmpty() && !passwordError) {
            try {
                Log.d("ChangePassWord", "Valid input, preparing to send request")

                val response = RetrofitInstance.api.login(input.toString(), password)
                return response
            } catch (e: Exception) {

                e.printStackTrace() // 打印錯誤堆疊，幫助調試
                val check = Check(false, e.toString())
                return check

            }
        }else{
            val check = Check(false, "e.toString()")
            return check
        }
    }










}



