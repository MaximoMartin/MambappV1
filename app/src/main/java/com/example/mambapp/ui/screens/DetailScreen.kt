package com.example.mambapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mambapp.Graph
import com.example.mambapp.data.entities.Monitoreo
import com.example.mambapp.viewmodel.*

@Composable
fun DetailScreen(
    monitoreo: Monitoreo?,
    onBack: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    val pacienteVM: PacienteViewModel = viewModel(factory = PacienteViewModelFactory(Graph.pacienteRepository))
    val medicoVM: MedicoViewModel = viewModel(factory = MedicoViewModelFactory(Graph.medicoRepository))
    val tecnicoVM: TecnicoViewModel = viewModel(factory = TecnicoViewModelFactory(Graph.tecnicoRepository))
    val lugarVM: LugarViewModel = viewModel(factory = LugarViewModelFactory(Graph.lugarRepository))
    val patologiaVM: PatologiaViewModel = viewModel(factory = PatologiaViewModelFactory(Graph.patologiaRepository))

    val pacientes by pacienteVM.pacientes.collectAsState()
    val medicos by medicoVM.medicos.collectAsState()
    val tecnicos by tecnicoVM.tecnicos.collectAsState()
    val lugares by lugarVM.lugares.collectAsState()
    val patologias by patologiaVM.patologias.collectAsState()

    monitoreo?.let {
        val paciente = pacientes.find { it.dniPaciente == monitoreo.dniPaciente }
        val medico = medicos.find { it.id == monitoreo.idMedico }
        val tecnico = tecnicos.find { it.id == monitoreo.idTecnico }
        val lugar = lugares.find { it.id == monitoreo.idLugar }
        val patologia = patologias.find { it.id == monitoreo.idPatologia }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Detalle del Monitoreo", fontWeight = FontWeight.Bold) },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                        }
                    }
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Registro: ${monitoreo.nroRegistro}", fontWeight = FontWeight.Bold)
                Text("Fecha realizado: ${monitoreo.fechaRealizado}")
                Text("Fecha presentado: ${monitoreo.fechaPresentado ?: "Sin registro"}")
                Text("Fecha cobrado: ${monitoreo.fechaCobrado ?: "Sin registro"}")
                Text("Paciente:")
                Text("- DNI: ${paciente?.dniPaciente ?: "Sin registro"}")
                Text("- Nombre: ${paciente?.nombre ?: "Sin registro"}")
                Text("- Apellido: ${paciente?.apellido ?: "Sin registro"}")
                Text("- Edad: ${paciente?.edad ?: "Sin registro"}")
                Text("- Mutual: ${paciente?.mutual ?: "Sin registro"}")
                Text("Médico: ${medico?.let { it.nombre + " " + it.apellido } ?: "Sin registro"}")
                Text("Técnico: ${tecnico?.let { it.nombre + " " + it.apellido } ?: "Sin registro"}")
                Text("Lugar: ${lugar?.nombre ?: "Sin registro"}")
                Text("Patología: ${patologia?.nombre ?: "Sin registro"}")
                Text("Anestesia: ${monitoreo.detalleAnestesia ?: "Sin registro"}")
                Text("Complicación: ${if (monitoreo.complicacion) "Sí" else "No"}")
                if (monitoreo.complicacion) {
                    Text("Detalle complicación: ${monitoreo.detalleComplicacion ?: "Sin registro"}")
                }
                Text("Cambio de motor: ${monitoreo.cambioMotor ?: "Sin registro"}")

                Spacer(modifier = Modifier.height(16.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = onEdit,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Editar")
                    }
                    Button(
                        onClick = onDelete,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text("Eliminar")
                    }
                }
            }
        }
    }
}
