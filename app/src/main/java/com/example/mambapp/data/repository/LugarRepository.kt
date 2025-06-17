package com.example.mambapp.data.repository

import com.example.mambapp.data.dao.LugarDao
import com.example.mambapp.data.entities.Lugar
import kotlinx.coroutines.flow.Flow

class LugarRepository(private val dao: LugarDao) {
    fun getAll(): Flow<List<Lugar>> = dao.getAll()
    suspend fun insert(lugar: Lugar) = dao.insert(lugar)
}