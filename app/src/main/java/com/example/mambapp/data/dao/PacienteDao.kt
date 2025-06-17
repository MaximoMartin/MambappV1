package com.example.mambapp.data.dao

import androidx.room.*
import com.example.mambapp.data.entities.Paciente
import kotlinx.coroutines.flow.Flow

@Dao
interface PacienteDao {
    @Query("SELECT * FROM pacientes")
    fun getAll(): Flow<List<Paciente>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(paciente: Paciente)

    @Query("SELECT * FROM pacientes WHERE dniPaciente = :dni LIMIT 1")
    suspend fun getByDni(dni: Int): Paciente?
}