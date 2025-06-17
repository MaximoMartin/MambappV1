package com.example.mambapp.data.repository

import com.example.mambapp.data.dao.MonitoreoDao
import com.example.mambapp.data.entities.Monitoreo
import kotlinx.coroutines.flow.Flow

class MonitoreoRepository(private val dao: MonitoreoDao) {

    fun getAllMonitoreos(): Flow<List<Monitoreo>> = dao.getAll()

    suspend fun getByRegistro(nro: Int): Monitoreo? = dao.getByRegistro(nro)

    suspend fun insert(monitoreo: Monitoreo) = dao.insert(monitoreo)

    suspend fun update(monitoreo: Monitoreo) = dao.update(monitoreo)

    suspend fun delete(monitoreo: Monitoreo) = dao.delete(monitoreo)
}