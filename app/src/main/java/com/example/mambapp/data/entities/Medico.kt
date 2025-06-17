package com.example.mambapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medicos")
data class Medico(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val nombre: String,
    val apellido: String
)