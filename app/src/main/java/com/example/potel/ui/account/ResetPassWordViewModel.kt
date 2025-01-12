package com.example.potel.ui.account

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class ResetPassWordViewModel : ViewModel() {

    private val _passwd = MutableStateFlow("")
    val passwd = _passwd.asStateFlow()
    var passwdError by mutableStateOf(false)
    fun passwdchange(passwd: String) {
        val passwdRegex = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,20}$")
        passwdError = !passwd.matches(passwdRegex)
        _passwd.value = passwd
    }


    private val _checkpasswd = MutableStateFlow("")
    val checkpasswd = _checkpasswd.asStateFlow()
    var checkpasswdError by mutableStateOf(false)
    fun checkpasswdchange(checkpasswd: String) {
        val checkpasswdRegex = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,20}$")
        checkpasswdError = !checkpasswd.matches(checkpasswdRegex)
        _checkpasswd.value = checkpasswd
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


    private val _changepasswd = MutableStateFlow(Change(success = false, message = ""))
    val changepasswd = _changepasswd.asStateFlow()

    suspend fun changepasswd(): Check {
        val email = _email.value
        val password = _passwd.value

        if (password.isNotEmpty() && !passwdError) {
            try {
                Log.d("ChangePassWord", "Valid input, preparing to send request")

                val response = RetrofitInstance.api.changepasswd(
                    Input(
                        email = email, passwd = password,
                    )
                )
                return response
            } catch (e: Exception) {

                e.printStackTrace() // 打印錯誤堆疊，幫助調試
                val check = Check(false, e.toString())
                return check

            }
        } else {
            val check = Check(false, "e.toString()")
            return check
        }
    }
}
