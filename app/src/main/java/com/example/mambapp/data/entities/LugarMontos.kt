package com.example.mambapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lugarMontos")
data class LugarMontos(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val idLugar: Int,
    val monto: Double,
    val fechaVigencia: String
)