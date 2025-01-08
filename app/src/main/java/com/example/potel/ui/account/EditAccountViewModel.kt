package com.example.potel.ui.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class EditAccountViewModel : ViewModel() {

    private val _uid = MutableStateFlow("")
    val uid = _uid.asStateFlow()

    private val _username = MutableStateFlow("")
    val username = _username.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _checkpassword = MutableStateFlow("")
    val checkpassword = _checkpassword.asStateFlow()

    private val _phonenumber = MutableStateFlow("")
    val phonenumber = _phonenumber.asStateFlow()

    private val _address = MutableStateFlow("")
    val address = _address.asStateFlow()

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()


    private val _gender = MutableStateFlow("")
    val gender = _gender.asStateFlow()

    private val _birthDate = MutableStateFlow("")
    val birthDate = _birthDate




    // 跟蹤錯誤訊息
    var errorMessage by mutableStateOf("")

    // 跟蹤欄位是否可編輯
    var isEditable = mutableStateOf(true)

    fun onUidChanged(uid: String) {
        _uid.value = uid
    }

    fun onEmailChanged(email: String) {
        _email.value = email
    }

    fun onPasswordChanged(password: String) {
        _password.value = password
    }

    fun onCheckPasswordChanged(checkPassword: String) {
        _checkpassword.value = checkPassword
    }

    fun onUsernameChanged(username: String) {
        _username.value = username
    }

    fun onPhoneNumberChanged(phonenumber: String) {
        _phonenumber.value = phonenumber
    }

    fun onAddressChanged(address: String) {
        _address.value = address
    }
    fun onBirthDateChanged(newDate: String) {
        _birthDate.value = newDate
    }

    // 處理性別變更
    fun onGenderChanged(newGender: String) {
        _gender.value = newGender
    }


    fun onError(message: String) {
        // 顯示錯誤訊息
        errorMessage = message
    }

    // 驗證欄位
    fun validateForm(): Boolean {
        val isUidValid = _uid.value.isNotEmpty()
        val isEmailValid = _email.value.isNotEmpty() && _email.value.contains("@")
        val isPasswordValid = _password.value == _checkpassword.value && _password.value.isNotEmpty()
        val isUsernameValid = _username.value.isNotEmpty()
        val isPhoneNumberValid = _phonenumber.value.isNotEmpty()
        val isAddressValid = _address.value.isNotEmpty()

        // 驗證失敗顯示錯誤訊息
        errorMessage = when {
            !isUidValid -> "用戶 ID 不能為空"
            !isEmailValid -> "電子郵件格式錯誤"
            !isPasswordValid -> "密碼不匹配或空白"
            !isUsernameValid -> "姓名不能為空"
            !isPhoneNumberValid -> "電話號碼不能為空"
            !isAddressValid -> "地址不能為空"
            else -> ""
        }

        return isUidValid && isEmailValid && isPasswordValid &&
                isUsernameValid && isPhoneNumberValid && isAddressValid
    }

    // 假設的 API 請求
    suspend fun apiCallToFetchUserData(): UserData {
        // 這裡你應該使用 Retrofit 或其他 HTTP 客戶端來獲取資料
        return UserData(
            uid = "12345",
            username = "John Doe",
            password = "password123",
            checkpassword = "password123",
            phonenumber = "0912345678",
            address = "台北市",
            email = "example@example.com"
        )
    }

    // 模擬從 API 載入用戶資料
    fun loadUserData() {
        viewModelScope.launch {
            val userData = apiCallToFetchUserData()
            _uid.value = userData.uid
            _username.value = userData.username
            _password.value = userData.password
            _checkpassword.value = userData.checkpassword
            _phonenumber.value = userData.phonenumber
            _address.value = userData.address
            _email.value = userData.email

            // 設定所有欄位為不可編輯
            isEditable.value = false
        }
    }
}

