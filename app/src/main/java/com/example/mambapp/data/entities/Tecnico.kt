package com.example.mambapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tecnicos")
data class Tecnico(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val nombre: String,
    val apellido: String
)