package com.example.potel.ui.account

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class EditViewModelFactory(
    private val preferences: SharedPreferences,
    private val apiService: ApiService
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EditViewModel(preferences, apiService) as T
    }
}