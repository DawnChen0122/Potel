package com.example.potel.ui.account



import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


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
        if (input.matches(phonenumberRegex)) {
            _inputError.value = false
            _phonenumber.value = input
        } else if (input.matches(emailRegex)) {
            _inputError.value = false
            _email.value = input
        } else {
            _inputError.value = true
        }
    }
}



