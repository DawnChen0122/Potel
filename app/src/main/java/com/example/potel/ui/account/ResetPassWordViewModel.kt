package com.example.potel.ui.account

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class ResetPassWordViewModel: ViewModel() {

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()
    var passwordError by mutableStateOf(false)
    fun onPasswordChanged(password: String) {
        val passwordRegex = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,20}$")
        passwordError = !password.matches(passwordRegex)
        _password.value = password
    }


    private val _checkpassword = MutableStateFlow("")
    val checkpassword = _checkpassword.asStateFlow()
    var checkpasswordError by mutableStateOf(false)
    fun onCheckPasswordChanged(checkpassword: String) {
        val checkpasswordRegex = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,20}$")
        checkpasswordError = !checkpassword.matches(checkpasswordRegex)
        _checkpassword.value = checkpassword
    }


    private val _phonenumber = MutableStateFlow("")
    val phonenumber = _phonenumber.asStateFlow()
    var phonenumberError by mutableStateOf(false)
    fun onPhonenumberChanged(phonenumber: String) {
        val phonenumberRegex = Regex("^[0-9]{10}$")
        phonenumberError = !phonenumber.matches(phonenumberRegex)
        _phonenumber.value = phonenumber
    }


    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()
    var emailError by mutableStateOf(false)
    fun onEmailChanged(email: String) {
        val emailRegex = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
        emailError = !email.matches(emailRegex)
        _email.value = email
    }


    private val _checkEmailAndCellphone = MutableStateFlow(Check(success = false, message = ""))
    val checkEmailAndCellphone = _checkEmailAndCellphone.asStateFlow()


    suspend fun checkEmailAndCellphone() {
//        val password = _password.value
        val email = _email.value
        val phonenumber = _phonenumber.value

        Log.d("checkEmailAndCellphone", "Checking email and phone number:")
        Log.d("checkEmailAndCellphone", "Email: $email, Phone number: $phonenumber")

        if (email.isNotEmpty() && phonenumber.isNotEmpty() && !emailError && !phonenumberError) {
            try {
                Log.d("checkEmailAndCellphone", "Valid input, preparing to send request")

                val response = RetrofitInstance.api.checkEmailAndCellphone(email, phonenumber)

                if (response != null) {
                    Log.d("checkEmailAndCellphone", "API response raw: $response")
                    Log.d(
                        "checkEmailAndCellphone",
                        "API response success = ${response.success}, message = ${response.message}"
                    )
                } else {
                    Log.e("checkEmailAndCellphone", "API response is null")
                }
                _checkEmailAndCellphone.value = response
            } catch (e: Exception) {
                // 捕獲並打印錯誤訊息
                Log.e("checkEmailAndCellphone", "API request failed: ${e.localizedMessage}")

                // 如果是 HTTP 404 錯誤，打印具體的錯誤代碼和 URL
                if (e is retrofit2.HttpException) {
                    Log.e(
                        "checkEmailAndCellphone",
                        "HTTP error: ${e.code()} - ${e.response()?.errorBody()?.string()}"
                    )
                    Log.e(
                        "checkEmailAndCellphone",
                        "Request URL: ${e.response()?.raw()?.request?.url}"
                    )
                }

                e.printStackTrace() // 打印錯誤堆疊，幫助調試
            }
        } else {
            Log.d("checkEmailAndCellphone", "Invalid email or phone number")
            println("Invalid email or phone number")
        }
    }
}