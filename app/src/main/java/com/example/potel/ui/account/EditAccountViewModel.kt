package com.example.potel.ui.account

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class EditViewModel(
    private val preferences: SharedPreferences,
    private val apiService: ApiService
) : ViewModel() {

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


    val member: StateFlow<Member> get() = _member


    fun updateCellphone(cellphone: String) {
        Log.d("EditViewModel", "更新手機號碼: $cellphone")
        _member.value = _member.value.copy(cellphone = cellphone)
        saveMemberToPreferences(_member.value)
    }


    fun updateEmail(email: String) {
        _member.value = _member.value.copy(email = email)
        saveMemberToPreferences(_member.value)
    }


    fun updatePassword(passwd: String) {
        _member.value = _member.value.copy(passwd = passwd)
        saveMemberToPreferences(_member.value)
    }


    fun updateAddress(address: String) {
        _member.value = _member.value.copy(address = address)
        saveMemberToPreferences(_member.value)
    }


    private fun saveMemberToPreferences(member: Member) {
        preferences.edit().apply {
            putInt("memberid", member.memberid)
            putString("name", member.name)
            putString("cellphone", member.cellphone)
            putString("email", member.email)
            putString("passwd", member.passwd)
            putString("address", member.address)
            putString("gender", member.gender)
            putString("birthday", member.birthday)
            apply()
        }
    }


    fun loadMember() {

        val loadedMember = Member(
            memberid = preferences.getString("memberid", "0")!!.toInt(),
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


    suspend fun edit(updatedMember: Edit): Member {
        return try {

            apiService.edit(updatedMember)

            _member.value = _member.value.copy(
                passwd = updatedMember.passwd,
                cellphone = updatedMember.cellphone,
                address = updatedMember.address,
                email = updatedMember.email
            )

            saveMemberToPreferences(_member.value)


            _edit.value = Change(success = true, message = "更新成功")
            _member.value
        } catch (e: Exception) {

            _edit.value = Change(success = false, message = "更新失敗: ${e.message}")
            throw e
        }
    }
}

