package com.example.mambapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mambapp.data.entities.Medico
import com.example.mambapp.data.repository.MedicoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MedicoViewModel(private val repository: MedicoRepository) : ViewModel() {

    private val _medicos = MutableStateFlow<List<Medico>>(emptyList())
    val medicos: StateFlow<List<Medico>> = _medicos.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAll().collect {
                _medicos.value = it
            }
        }
    }

    fun addMedico(nombre: String, apellido: String) {
        viewModelScope.launch {
            repository.insert(Medico(id = 0, nombre = nombre, apellido = apellido))
        }
    }
}
