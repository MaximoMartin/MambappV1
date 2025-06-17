package com.example.mambapp.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mambapp.data.entities.Tecnico
import com.example.mambapp.data.repository.TecnicoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TecnicoViewModel(private val repository: TecnicoRepository) : ViewModel() {
    private val _tecnicos = MutableStateFlow<List<Tecnico>>(emptyList())
    val tecnicos: StateFlow<List<Tecnico>> = _tecnicos.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAll().collect {
                _tecnicos.value = it
            }
        }
    }

    fun addTecnico(nombre: String, apellido: String) {
        viewModelScope.launch {
            repository.insert(Tecnico(id = 0, nombre = nombre, apellido = apellido))
        }
    }
}