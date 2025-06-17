package com.example.mambapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lugares")
data class Lugar(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val nombre: String,
    val provincia: String
)