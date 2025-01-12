package com.example.potel.ui.account

data class LoginResponse(
    val success: Boolean,
    val token: String,
    val errorMessage: String?
)

