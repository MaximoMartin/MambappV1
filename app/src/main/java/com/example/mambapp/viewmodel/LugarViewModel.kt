package com.example.mambapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mambapp.data.entities.Lugar
import com.example.mambapp.data.repository.LugarRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LugarViewModel(private val repository: LugarRepository) : ViewModel() {
    private val _lugares = MutableStateFlow<List<Lugar>>(emptyList())
    val lugares: StateFlow<List<Lugar>> = _lugares.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAll().collect {
                _lugares.value = it
            }
        }
    }

    fun addLugar(nombre: String, provincia: String) {
        viewModelScope.launch {
            repository.insert(Lugar(id = 0, nombre = nombre, provincia = provincia))
        }
    }
}