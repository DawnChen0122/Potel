package com.example.potel.ui.account

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class EditViewModel(private val preferences: SharedPreferences) : ViewModel() {

    private val _member = MutableStateFlow<Member>(
        Member(
            memberid = 0,
            name = "",
            passwd = "",
            cellphone = "",
            address = "",
            email = "",
            gender = "",
            birthday = ""
        )
    )

    // 外部可讀取的 StateFlow，監控 Member 資料變化
    val member: StateFlow<Member> get() = _member


    // 更新手機號碼
    fun updateCellphone(cellphone: String) {
        _member.value = _member.value.copy(cellphone = cellphone)
        saveMemberToPreferences(_member.value)
    }

    // 更新電子郵件
    fun updateEmail(email: String) {
        _member.value = _member.value.copy(email = email)
        saveMemberToPreferences(_member.value)
    }

    // 更新密碼
    fun updatePassword(passwd: String) {
        _member.value = _member.value.copy(passwd = passwd)
        saveMemberToPreferences(_member.value)
    }

    // 更新地址
    fun updateAddress(address: String) {
        _member.value = _member.value.copy(address = address)
        saveMemberToPreferences(_member.value)
    }

    // 儲存 Member 資料到 SharedPreferences
    private fun saveMemberToPreferences(member: Member) {
        preferences.edit().apply {
            putInt("memberid", member.memberid)
            putString("name", member.name)  // 顯示 name 但不會允許更新
            putString("cellphone", member.cellphone)
            putString("email", member.email)
            putString("passwd", member.passwd)
            putString("address", member.address)
            putString("gender", member.gender)  // 顯示 gender 但不會允許更新
            putString("birthday", member.birthday)  // 顯示 birthday 但不會允許更新
            apply()
        }
    }
}
