package com.example.mambapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mambapp.data.entities.Medico
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicoDao {
    @Query("SELECT * FROM medicos")
    fun getAll(): Flow<List<Medico>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(medico: Medico)
}