package com.example.potel.ui.account


import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AccountViewModel : ViewModel() {

    private val _uid = MutableStateFlow("")
    val uid = _uid.asStateFlow()
    var uidError by mutableStateOf(false)
    fun onUidChanged(uid: String) {
        val uidRegex = Regex("^[a-zA-Z0-9]{3,20}$")
        uidError = !uid.matches(uidRegex)
        _uid.value = uid
    }


    private val _username = MutableStateFlow("")
    val username = _username.asStateFlow()
    fun onUsernameChanged(username: String) {
        _username.value = username
    }


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
        val checkpasswordRegex = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
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


    private val _address = MutableStateFlow("")
    val address = _address.asStateFlow()
    fun onAddressChanged(address: String) {
        _address.value = address
    }


    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()
    var emailError by mutableStateOf(false)
    fun onEmailChanged(email: String) {
        val emailRegex = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
        emailError = !email.matches(emailRegex)
        _email.value = email
    }

    private val _addmember = MutableStateFlow(Change(success = false, message = ""))
    val addmember = _addmember.asStateFlow()

    suspend fun addmember(member: Member): Check {


        if (member.passwd.isNotEmpty() && !passwordError) {
            try {
                Log.d("ChangePassWord", "Valid input, preparing to send request")

                val response =
                    RetrofitInstance.api.addmember(member)
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