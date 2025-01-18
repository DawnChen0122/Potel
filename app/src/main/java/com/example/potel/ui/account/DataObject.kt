package com.example.potel.ui.account

data class Check(
    val success: Boolean,
    val message: String?
)

data class Member(
    val memberid: Int = 0,
    val name: String,
    val passwd: String,
    val cellphone: String,
    val address: String,
    val email: String,
    val gender: String,
    val birthday: String,
)


data class InputRequest(
    val email: String,
    val passwd: String
)


data class Inputlog(
    val input: String,
    val passwd: String
)

data class Change(
    val success: Boolean,
    val message: String?
)

data class Edit1(
    val passwd: String,
    val cellphone: String,
    val address: String,
    val email: String,
    val memberid: Int
)

