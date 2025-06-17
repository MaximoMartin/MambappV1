package com.example.mambapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mambapp.data.entities.Monitoreo
import com.example.mambapp.data.repository.MonitoreoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MonitoreoViewModel(
    private val repository: MonitoreoRepository
) : ViewModel() {

    private val _monitoreos = MutableStateFlow<List<Monitoreo>>(emptyList())
    val monitoreos: StateFlow<List<Monitoreo>> = _monitoreos.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAllMonitoreos().collect {
                _monitoreos.value = it
            }
        }
    }

    fun addMonitoreo(monitoreo: Monitoreo) = viewModelScope.launch {
        repository.insert(monitoreo)
    }

    fun updateMonitoreo(monitoreo: Monitoreo) = viewModelScope.launch {
        repository.update(monitoreo)
    }

    fun deleteMonitoreoByRegistro(nro: Int) = viewModelScope.launch {
        repository.getByRegistro(nro)?.let {
            repository.delete(it)
        }
    }

    fun getMonitoreoByNro(nro: Int): Monitoreo? {
        return monitoreos.value.find { it.nroRegistro == nro }
    }
}