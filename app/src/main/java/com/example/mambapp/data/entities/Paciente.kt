package com.example.mambapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pacientes")
data class Paciente(
    @PrimaryKey(autoGenerate = true) val dniPaciente: Int,
    val nombre: String,
    val apellido: String,
    val edad: Int,
    val mutual: String
)
