package com.example.mambapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mambapp.data.entities.Paciente
import com.example.mambapp.data.repository.PacienteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PacienteViewModel(private val repository: PacienteRepository) : ViewModel() {

    private val _pacientes = MutableStateFlow<List<Paciente>>(emptyList())
    val pacientes: StateFlow<List<Paciente>> = _pacientes.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAll().collect {
                _pacientes.value = it
            }
        }
    }

    fun addPaciente(paciente: Paciente) {
        viewModelScope.launch {
            repository.insert(paciente)
        }
    }
}