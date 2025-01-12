package com.example.potel.ui.account


import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AccountViewModel : ViewModel() {


    private val _gender = MutableStateFlow("")
    val gender = _gender.asStateFlow()
    fun genderchange(gender: String) {
        val genderMap = mapOf(
            "男" to "M",
            "女" to "F",
            "不願透漏" to "N"
        )
        _gender.value = genderMap[gender] ?: "N"
    }


    private val _birthday = MutableStateFlow("")
    val birthday = _birthday.asStateFlow()

    fun birthdaychange(inputYear: String, inputMonth: String, inputDay: String) {
        if (inputYear.isNotEmpty() && inputMonth.isNotEmpty() && inputDay.isNotEmpty()) {
            _birthday.value = "$inputYear-$inputMonth-$inputDay"
        } else {
            _birthday.value = ""
        }
    }



    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()
    fun namechange(name: String) {
        _name.value = name
    }


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
    fun checkpasswdchange(checkpassword: String) {
        val checkpasswdRegex = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
        checkpasswdError = !checkpassword.matches(checkpasswdRegex)
        _checkpasswd.value = checkpassword
    }


    private val _cellphone = MutableStateFlow("")
    val cellphone = _cellphone.asStateFlow()
    var cellphoneError by mutableStateOf(false)
    fun cellphonechange(cellphone: String) {
        val cellphoneRegex = Regex("^[0-9]{10}$")
        cellphoneError = !cellphone.matches(cellphoneRegex)
        _cellphone.value = cellphone
    }


    private val _address = MutableStateFlow("")
    val address = _address.asStateFlow()
    fun addresschange(address: String) {
        _address.value = address
    }


    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()
    var emailError by mutableStateOf(false)
    fun emailchange(email: String) {
        val emailRegex = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
        emailError = !email.matches(emailRegex)
        _email.value = email
    }

    private val _addmember = MutableStateFlow(Change(success = false, message = ""))
    val addmember = _addmember.asStateFlow()

    suspend fun addmember(member: Member): Check {


        if (member.passwd.isNotEmpty() && !passwdError) {
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