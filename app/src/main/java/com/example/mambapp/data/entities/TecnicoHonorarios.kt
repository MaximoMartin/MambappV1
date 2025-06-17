package com.example.mambapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tecnicoHonorarios")
data class TecnicoHonorarios(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val idTecnico: Int,
    val honorario: Double,
    val fechaVigencia: String
)