package com.example.potel.ui.account

data class LoginResponse(
    val success: Boolean,
    val token: String,
    val errorMessage: String?
)

data class UserData(
    val uid: String,
    val username: String,
    val password: String,
    val checkpassword: String,
    val phonenumber: String,
    val address: String,
    val email: String
)