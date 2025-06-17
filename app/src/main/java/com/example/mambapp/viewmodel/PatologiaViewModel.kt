package com.example.mambapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mambapp.data.entities.Patologia
import com.example.mambapp.data.repository.PatologiaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PatologiaViewModel(private val repository: PatologiaRepository) : ViewModel() {
    private val _patologias = MutableStateFlow<List<Patologia>>(emptyList())
    val patologias: StateFlow<List<Patologia>> = _patologias.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAll().collect {
                _patologias.value = it
            }
        }
    }

    fun addPatologia(nombre: String) {
        viewModelScope.launch {
            repository.insert(Patologia(id = 0, nombre = nombre))
        }
    }
}