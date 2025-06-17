package com.example.mambapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "patologias")
data class Patologia(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val nombre: String
)