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
    val birthday: String? = null
)


data class Input(
    val email: String,
    val passwd: String
)


data class Change(

    val success: Boolean,
    val message: String?
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

//data class Member(
//var memberid: Int = 0,
//var name: String = "",
//var cellphone: String = "",
//var address: String = "",
//var gender: Char = 'M',
//var birthday: String? = null,
//var email: String = "",
//var imageid: Int = 0,
//var status: Char = '1',
//var createdate: String? = null,
//var modifydate: String? = null,
//val passwd: String,
//
//)
