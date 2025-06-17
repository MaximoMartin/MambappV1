package com.example.mambapp

import android.content.Context
import com.example.mambapp.data.repository.MedicoRepository
import com.example.mambapp.data.database.AppDatabase
import com.example.mambapp.data.repository.LugarRepository
import com.example.mambapp.data.repository.MonitoreoRepository
import com.example.mambapp.data.repository.PacienteRepository
import com.example.mambapp.data.repository.PatologiaRepository
import com.example.mambapp.data.repository.TecnicoRepository

object Graph {

    lateinit var database: AppDatabase
        private set

    lateinit var monitoreoRepository: MonitoreoRepository
        private set

    lateinit var medicoRepository: MedicoRepository

    lateinit var tecnicoRepository: TecnicoRepository

    lateinit var lugarRepository: LugarRepository

    lateinit var patologiaRepository: PatologiaRepository

    lateinit var pacienteRepository: PacienteRepository


    fun provide(context: Context) {
        database = AppDatabase.getDatabase(context)
        monitoreoRepository = MonitoreoRepository(database.monitoreoDao())
        medicoRepository = MedicoRepository(database.medicoDao())
        tecnicoRepository = TecnicoRepository(database.tecnicoDao())
        lugarRepository = LugarRepository(database.lugarDao())
        patologiaRepository = PatologiaRepository(database.patologiaDao())
        pacienteRepository = PacienteRepository(database.pacienteDao())

    }
}
