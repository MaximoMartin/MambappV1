package com.example.mambapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mambapp.data.repository.LugarRepository

class LugarViewModelFactory(
    private val repository: LugarRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LugarViewModel::class.java)) {
            return LugarViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}