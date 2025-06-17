package com.example.mambapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mambapp.data.entities.Lugar
import kotlinx.coroutines.flow.Flow

@Dao
interface LugarDao {
    @Query("SELECT * FROM lugares")
    fun getAll(): Flow<List<Lugar>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(lugar: Lugar)
}