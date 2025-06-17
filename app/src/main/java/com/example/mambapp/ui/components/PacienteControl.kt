package com.example.mambapp.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import com.example.mambapp.data.entities.Paciente

@Composable
fun PacienteControl(
    paciente: Paciente?,
    onAgregar: () -> Unit,
    onEditar: () -> Unit
) {
    Text("Paciente: ${paciente?.let { "${it.nombre} ${it.apellido} (${it.dniPaciente})" } ?: "Sin registro"}")

    Row {
        if (paciente == null) {
            IconButton(onClick = onAgregar) {
                Text("+ Agregar Paciente")
            }
        } else {
            IconButton(onClick = onEditar) {
                Text("✏️ Editar Paciente")
            }
        }
    }
}
