package com.example.potel.ui.account

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class AccountViewModel : ViewModel() {

    private val _uid = MutableStateFlow<List<TipHomeItemUiState>>(listOf())
    val uid = _uid.asStateFlow()
    fun onUidChanged(uid: String){
        _uid.value = listOf(TipHomeItemUiState(title = uid, imageVector = Icons.Filled.Email))
    }


    private val _username = MutableStateFlow<List<TipHomeItemUiState>>(listOf())
    val username = _username.asStateFlow()
    fun onUsernameChanged(username: String) {
        _username.value = listOf(TipHomeItemUiState(title = username, imageVector = Icons.Filled.Email))
    }



    private val _password = MutableStateFlow<List<TipHomeItemUiState>>(listOf())
    val password = _password.asStateFlow()
    fun onPasswordChanged(password: String){
        _password.value = listOf(TipHomeItemUiState(title = password, imageVector = Icons.Filled.Email))
    }


    private val _checkpassword = MutableStateFlow<List<TipHomeItemUiState>>(listOf())
    val checkpassword = _checkpassword.asStateFlow()
    fun onCheckPasswordChanged(checkpassword: String){
        _checkpassword.value = listOf(TipHomeItemUiState(title = checkpassword, imageVector = Icons.Filled.Email))
    }




    private val _phonenumber = MutableStateFlow<List<TipHomeItemUiState>>(listOf())
    val phonenumber = _phonenumber.asStateFlow()
    fun onPhonenumberChanged(phonenumber: String) {
        _phonenumber.value =
            listOf(TipHomeItemUiState(title = phonenumber, imageVector = Icons.Filled.Email))
    }



    private val _address = MutableStateFlow<List<TipHomeItemUiState>>(listOf())
    val address = _address.asStateFlow()
    fun onAddressumberChanged(address: String){
        _address.value = listOf(TipHomeItemUiState(title = address, imageVector = Icons.Filled.Email))
    }

//    private val _items = MutableStateFlow<List<TipHomeItemUiState>>(listOf())
//    val items = _items.asStateFlow()

    private val _email = MutableStateFlow<List<TipHomeItemUiState>>(listOf())
    val email: StateFlow<List<TipHomeItemUiState>> = _email.asStateFlow()
    fun onEmailChanged(email: String){
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