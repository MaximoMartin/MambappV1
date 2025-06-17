package com.example.mambapp.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "monitoreos",
    foreignKeys = [
        ForeignKey(entity = Paciente::class, parentColumns = ["dniPaciente"], childColumns = ["dniPaciente"]),
        ForeignKey(entity = Medico::class, parentColumns = ["id"], childColumns = ["idMedico"]),
        ForeignKey(entity = Tecnico::class, parentColumns = ["id"], childColumns = ["idTecnico"]),
        ForeignKey(entity = Lugar::class, parentColumns = ["id"], childColumns = ["idLugar"]),
        ForeignKey(entity = Patologia::class, parentColumns = ["id"], childColumns = ["idPatologia"])
    ]
)
data class Monitoreo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nroRegistro: Int,
    val fechaRealizado: String,
    val fechaPresentado: String?= null,
    val fechaCobrado: String?= null,
    val dniPaciente: Int,
    val idMedico: Int,
    val idTecnico: Int,
    val idLugar: Int,
    val idPatologia: Int,
    val detalleAnestesia: String? = null,
    val complicacion: Boolean = false,
    val detalleComplicacion: String? = null,
    val cambioMotor: String? = null
)
