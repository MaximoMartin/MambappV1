package com.example.mambapp.data.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mambapp.data.entities.Tecnico
import kotlinx.coroutines.flow.Flow

@Dao
interface TecnicoDao {
    @Query("SELECT * FROM tecnicos")
    fun getAll(): Flow<List<Tecnico>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tecnico: Tecnico)
}