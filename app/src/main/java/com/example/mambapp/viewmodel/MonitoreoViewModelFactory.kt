package com.example.mambapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mambapp.data.repository.MonitoreoRepository

class MonitoreoViewModelFactory(
    private val repository: MonitoreoRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MonitoreoViewModel::class.java)) {
            return MonitoreoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}