package com.example.mambapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mambapp.data.entities.Monitoreo
import kotlinx.coroutines.flow.Flow

@Dao
interface MonitoreoDao {
    @Query("SELECT * FROM monitoreos ORDER BY fechaRealizado DESC")
    fun getAll(): Flow<List<Monitoreo>>

    @Query("SELECT * FROM monitoreos WHERE nroRegistro = :registro")
    suspend fun getByRegistro(registro: Int): Monitoreo?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(monitoreo: Monitoreo)

    @Update
    suspend fun update(monitoreo: Monitoreo)

    @Delete
    suspend fun delete(monitoreo: Monitoreo)
}
