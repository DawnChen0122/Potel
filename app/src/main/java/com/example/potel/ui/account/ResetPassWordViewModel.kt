package com.example.potel.ui.account

import android.R.id.input
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
    fun checkpasswdchange (checkpasswd: String) {
        checkpasswdError = checkpasswd != _passwd.value
        _checkpasswd.value = checkpasswd
    }


    private val _cellphone = MutableStateFlow("")
    val cellphone = _cellphone.asStateFlow()
    var cellphoneError by mutableStateOf(false)
    fun oncellphoneChanged(cellphone: String) {
        val cellphoneRegex = Regex("^[0-9]{10}$")
        cellphoneError = !cellphone.matches(cellphoneRegex)
        _cellphone.value = cellphone
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


    suspend fun checkEmailAndCellphone(): Check {

        val email = _email.value
        val cellphone = _cellphone.value

        Log.d("checkEmailAndCellphone", "Checking email and phone number:")
        Log.d("checkEmailAndCellphone", "Email: $email, Phone number: $ cellphone")

        if (email.isNotEmpty() && cellphone.isNotEmpty() && !emailError && !cellphoneError) {
            try {
                Log.d("checkEmailAndCellphone", "Valid input, preparing to send request")

                val response = RetrofitInstance.api.checkEmailAndCellphone(email, cellphone)

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
                return response
            } catch (e: Exception) {
                Log.e("checkEmailAndCellphone", "API request failed: ${e.localizedMessage}")
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

                e.printStackTrace()
            }
        } else {
            Log.d("checkEmailAndCellphone", "Invalid email or phone number")
            _checkEmailAndCellphone.value =
                Check(success = false, message = "電子郵件或手機號碼無效")
        }
        return _checkEmailAndCellphone.value
    }

    private val _changepasswd = MutableStateFlow(Change(success = false, message = ""))
    val changepasswd = _changepasswd.asStateFlow()

    suspend fun changepasswd(): Check {
        val passwd = _passwd.value
        val email = _email.value

        if (passwd.isNotEmpty() && !passwdError) {
            try {
                Log.d("ChangePassWd", "Valid input, preparing to send request")

                val response = RetrofitInstance.api.changepasswd(
                    InputRequest(
                        email = email, passwd = passwd,
                    )
                )
                return response
            } catch (e: Exception) {
                e.printStackTrace()
                val check = Check(false, e.toString())
                return check


            }
        } else {
            Log.d("checkEmailAndCellphone", "123")

            val check = Check(false, "e.toString()")
            return check
        }
    }
}
