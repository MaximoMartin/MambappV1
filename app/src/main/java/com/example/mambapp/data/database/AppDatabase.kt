package com.example.mambapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mambapp.data.dao.LugarDao
import com.example.mambapp.data.dao.MedicoDao
import com.example.mambapp.data.dao.MonitoreoDao
import com.example.mambapp.data.dao.PacienteDao
import com.example.mambapp.data.dao.PatologiaDao
import com.example.mambapp.data.dao.TecnicoDao
import com.example.mambapp.data.entities.Lugar
import com.example.mambapp.data.entities.Medico
import com.example.mambapp.data.entities.Monitoreo
import com.example.mambapp.data.entities.Paciente
import com.example.mambapp.data.entities.Patologia
import com.example.mambapp.data.entities.Tecnico

@Database(
    entities = [Monitoreo::class, Paciente::class, Medico::class, Tecnico::class, Lugar::class, Patologia::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun monitoreoDao(): MonitoreoDao
    abstract fun medicoDao(): MedicoDao
    abstract fun tecnicoDao(): TecnicoDao
    abstract fun lugarDao(): LugarDao
    abstract fun patologiaDao(): PatologiaDao
    abstract fun pacienteDao(): PacienteDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "mambapp_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
