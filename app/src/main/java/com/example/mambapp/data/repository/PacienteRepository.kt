package com.example.mambapp.data.repository

import com.example.mambapp.data.dao.PacienteDao
import com.example.mambapp.data.entities.Paciente
import kotlinx.coroutines.flow.Flow

class PacienteRepository(private val dao: PacienteDao) {
    fun getAll(): Flow<List<Paciente>> = dao.getAll()
    suspend fun insert(paciente: Paciente) = dao.insert(paciente)
    suspend fun getByDni(dni: Int): Paciente? = dao.getByDni(dni)
}