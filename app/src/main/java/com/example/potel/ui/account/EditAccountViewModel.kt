package com.example.potel.ui.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class EditAccountViewModel : ViewModel() {

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
}
