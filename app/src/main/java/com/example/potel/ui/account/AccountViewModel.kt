package com.example.potel.ui.account

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AccountViewModel : ViewModel() {


    private val _uid = MutableStateFlow<List<TipHomeItemUiState>>(listOf())
    val uid: StateFlow<List<TipHomeItemUiState>> = _uid.asStateFlow()
    var uidError by mutableStateOf(false)
    fun onUidChanged(uid: String){
        val uidRegex = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{3,20}$")
        uidError = !uid.matches(uidRegex)
        _uid.value = listOf(TipHomeItemUiState(title = uid, imageVector = Icons.Filled.Email))
    }


    private val _username = MutableStateFlow<List<TipHomeItemUiState>>(listOf())
    val username = _username.asStateFlow()
    fun onUsernameChanged(username: String) {
        _username.value = listOf(TipHomeItemUiState(title = username, imageVector = Icons.Filled.Email))
    }



private val _password = MutableStateFlow<List<TipHomeItemUiState>>(listOf())
    val password: StateFlow<List<TipHomeItemUiState>> = _password.asStateFlow()
    var passwordError by mutableStateOf(false)
    fun onPasswordChanged(password: String){
        val passwordRegex = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,20}$")
        passwordError = !password.matches(passwordRegex)
        _password.value = listOf(TipHomeItemUiState(title = password, imageVector = Icons.Filled.Email))
    }



    private val _checkpassword = MutableStateFlow<List<TipHomeItemUiState>>(listOf())
    val checkpassword: StateFlow<List<TipHomeItemUiState>> = _checkpassword.asStateFlow()
    var checkpasswordError by mutableStateOf(false)
    fun onCheckPasswordChanged(checkpassword: String){
        checkpasswordError = checkpassword != _password.value.firstOrNull()?.title
        _checkpassword.value = listOf(TipHomeItemUiState(title = checkpassword, imageVector = Icons.Filled.Email))
    }


    private val _phonenumber = MutableStateFlow<List<TipHomeItemUiState>>(listOf())
    val phonenumber: StateFlow<List<TipHomeItemUiState>> = _phonenumber.asStateFlow()
    var phonenumberError by mutableStateOf(false)
    fun onPhonenumberChanged(phonenumber:String) {
        val phonenumberRegex = Regex("^[0-9]{10}$")
        phonenumberError = !phonenumber.matches(phonenumberRegex)
        _phonenumber.value = listOf(TipHomeItemUiState(title = phonenumber, imageVector = Icons.Filled.Email))
    }



    private val _address = MutableStateFlow<List<TipHomeItemUiState>>(listOf())
    val address = _address.asStateFlow()
    fun onAddressChanged(address: String){
        _address.value = listOf(TipHomeItemUiState(title = address, imageVector = Icons.Filled.Email))
    }

//    private val _items = MutableStateFlow<List<TipHomeItemUiState>>(listOf())
//    val items = _items.asStateFlow()

    private val _email = MutableStateFlow<List<TipHomeItemUiState>>(listOf())
    val email: StateFlow<List<TipHomeItemUiState>> = _email.asStateFlow()
    var emailError by mutableStateOf(false)
    fun onEmailChanged(email: String){
        val emailRegex = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
        emailError = !email.matches(emailRegex)
        _email.value = listOf(TipHomeItemUiState(title = email, imageVector = Icons.Filled.Email))
    }
}

data class TipHomeItemUiState(val title: String, val imageVector: ImageVector)

// 假設的 ApiService，負責處理登入請求
//object ApiService3 {
//    fun Login3(callback: (Result) -> Unit) {
//        // 模擬的 API 呼叫，假設登入成功
//        // 這裡您可以處理 API 的回應
//        callback(Result())
//    }
//}

// 假設的 Result 類別，用來表示 API 回應
class Result

//    fun getApiData() {
//        // todo 2-5 取得 API 資料，目前先用假資料
//        _items.update {
//            listOf(
//                TipHomeItemUiState(
//                    title = "Home",
//                    imageVector = Icons.Filled.Home
//                ),
//                TipHomeItemUiState(
//                    title = "Search",
//                    imageVector = Icons.Filled.Search
//                ),
//                TipHomeItemUiState(
//                    title = "Delete",
//                    imageVector = Icons.Filled.Delete
//                ),
//            )
//        }
//    }

//private fun ApiService.Companion.Login(string: String) {}
//API 翻轉頁面 資料交互

//    fun onConfirmClick() {
//        ApiService.Login { result ->
//            // 更新 _uid 資料，可以根據 API 回應來改變 uid
//            _uid.value = listOf(
//                TipHomeItemUiState(title = "User ID", imageVector = Icons.Filled.Person),
////                TipHomeItemUiState(title = "User Data", imageVector = Icons.Filled.DataUsage)
//            )
//        }
//    }
//}