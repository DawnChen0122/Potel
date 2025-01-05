//package com.example.potel.ui.account
//
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.launch
//
//class AccountViewModel : ViewModel() {
//
//
//    private val _phonenumber = MutableStateFlow("")
//    val phonenumber = _phonenumber.asStateFlow()
//    var phonenumberError by mutableStateOf(false)
//    fun onPhonenumberChanged(phonenumber: String) {
//        val phonenumberRegex = Regex("^[0-9]{10}$")
//        phonenumberError = !phonenumber.matches(phonenumberRegex)
//        _phonenumber.value = phonenumber}
//
//
//    private val _email = MutableStateFlow("")
//    val email = _email.asStateFlow()
//    var emailError by mutableStateOf(false)
//    fun onEmailChanged(email: String) {
//        val emailRegex = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
//        emailError = !email.matches(emailRegex)
//        _email.value = email
//    }
//
//
//
