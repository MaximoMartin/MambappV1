package com.example.mambapp.data.repository

import com.example.mambapp.data.dao.MedicoDao
import com.example.mambapp.data.entities.Medico
import kotlinx.coroutines.flow.Flow

class MedicoRepository(private val dao: MedicoDao) {
    fun getAll(): Flow<List<Medico>> = dao.getAll()
    suspend fun insert(medico: Medico) = dao.insert(medico)
}