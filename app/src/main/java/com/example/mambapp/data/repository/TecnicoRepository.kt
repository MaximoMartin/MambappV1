package com.example.mambapp.data.repository

import com.example.mambapp.data.dao.TecnicoDao
import com.example.mambapp.data.entities.Tecnico
import kotlinx.coroutines.flow.Flow

class TecnicoRepository(private val dao: TecnicoDao) {
    fun getAll(): Flow<List<Tecnico>> = dao.getAll()
    suspend fun insert(tecnico: Tecnico) = dao.insert(tecnico)
}