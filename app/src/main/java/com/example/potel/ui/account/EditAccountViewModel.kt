package com.example.potel.ui.account

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class EditViewModel(private val preferences: SharedPreferences) : ViewModel() {

    private val _member = MutableStateFlow(
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
        Log.d("EditViewModel", "更新手機號碼: $cellphone")
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

    // 載入 Member 資料（假設是從 SharedPreferences 或其他地方）
    fun loadMember() {
        // 假設資料來自 SharedPreferences 或從網絡獲取
        val loadedMember = Member(
            memberid = preferences.getInt("memberid", 0),
            name = preferences.getString("name", "") ?: "",
            passwd = preferences.getString("passwd", "") ?: "",
            cellphone = preferences.getString("cellphone", "") ?: "",
            address = preferences.getString("address", "") ?: "",
            email = preferences.getString("email", "") ?: "",
            gender = preferences.getString("gender", "") ?: "",
            birthday = preferences.getString("birthday", "") ?: ""
        )
        Log.d("EditViewModel", "載入會員資料: $loadedMember")
        _member.value = loadedMember
    }


    private val _edit = MutableStateFlow(Change(success = false, message = ""))
    val edit = _edit.asStateFlow()

    // 更新會員資料
    suspend fun edit(updatedMember: Edit): Member {
        return try {
            // 假設這裡會進行資料庫或網絡請求來更新資料
            // 更新 _member 以反映最新的資料
            _member.value = _member.value.copy(
                passwd = updatedMember.passwd,
                cellphone = updatedMember.cellphone,
                address = updatedMember.address,
                email = updatedMember.email
            )

            // 儲存更新後的資料到 SharedPreferences
            saveMemberToPreferences(_member.value)

            // 返回更新後的資料
            _edit.value = Change(success = true, message = "更新成功")
            _member.value
        } catch (e: Exception) {
            // 錯誤處理
            _edit.value = Change(success = false, message = "更新失敗: ${e.message}")
            throw e
        }
    }
}

