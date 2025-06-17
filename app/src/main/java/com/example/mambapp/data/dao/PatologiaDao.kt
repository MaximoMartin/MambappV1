package com.example.mambapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mambapp.data.entities.Patologia
import kotlinx.coroutines.flow.Flow

@Dao
interface PatologiaDao {
    @Query("SELECT * FROM patologias")
    fun getAll(): Flow<List<Patologia>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(patologia: Patologia)
}