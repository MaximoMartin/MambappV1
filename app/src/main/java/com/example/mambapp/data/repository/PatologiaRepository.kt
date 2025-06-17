package com.example.mambapp.data.repository

import com.example.mambapp.data.dao.PatologiaDao
import com.example.mambapp.data.entities.Patologia
import kotlinx.coroutines.flow.Flow

class PatologiaRepository(private val dao: PatologiaDao) {
    fun getAll(): Flow<List<Patologia>> = dao.getAll()
    suspend fun insert(patologia: Patologia) = dao.insert(patologia)
}