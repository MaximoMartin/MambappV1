package com.example.mambapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mambapp.Graph
import com.example.mambapp.R
import com.example.mambapp.data.entities.Monitoreo
import com.example.mambapp.data.entities.Paciente
import com.example.mambapp.ui.components.DateSelector
import com.example.mambapp.ui.components.EntityDropdown
import com.example.mambapp.ui.components.SimpleInputDialog
import com.example.mambapp.viewmodel.*

@Composable
fun FormScreen(
    monitoreo: Monitoreo?,
    onSave: (Monitoreo) -> Unit,
    onCancel: () -> Unit
) {
    val pacienteVM: PacienteViewModel = viewModel(factory = PacienteViewModelFactory(Graph.pacienteRepository))
    val pacientes by pacienteVM.pacientes.collectAsState()

    val medicoVM: MedicoViewModel = viewModel(factory = MedicoViewModelFactory(Graph.medicoRepository))
    val tecnicoVM: TecnicoViewModel = viewModel(factory = TecnicoViewModelFactory(Graph.tecnicoRepository))
    val lugarVM: LugarViewModel = viewModel(factory = LugarViewModelFactory(Graph.lugarRepository))
    val patologiaVM: PatologiaViewModel = viewModel(factory = PatologiaViewModelFactory(Graph.patologiaRepository))

    val medicos by medicoVM.medicos.collectAsState()
    val tecnicos by tecnicoVM.tecnicos.collectAsState()
    val lugares by lugarVM.lugares.collectAsState()
    val patologias by patologiaVM.patologias.collectAsState()

    var fechaRealizado by rememberSaveable { mutableStateOf(monitoreo?.fechaRealizado ?: "") }
    var fechaPresentado by rememberSaveable { mutableStateOf(monitoreo?.fechaPresentado ?: "") }
    var fechaCobrado by rememberSaveable { mutableStateOf(monitoreo?.fechaCobrado ?: "") }
    var dniPaciente by rememberSaveable { mutableStateOf(monitoreo?.dniPaciente?.toString() ?: "") }
    var selectedMedicoId by rememberSaveable { mutableStateOf(monitoreo?.idMedico ?: -1) }
    var selectedTecnicoId by rememberSaveable { mutableStateOf(monitoreo?.idTecnico ?: -1) }
    var selectedLugarId by rememberSaveable { mutableStateOf(monitoreo?.idLugar ?: -1) }
    var selectedPatologiaId by rememberSaveable { mutableStateOf(monitoreo?.idPatologia ?: -1) }
    var showDialogTipo by rememberSaveable { mutableStateOf("") }
    var camposDialog = remember { mutableStateListOf<Pair<String, MutableState<String>>>() }

    var anestesia by rememberSaveable { mutableStateOf(monitoreo?.detalleAnestesia ?: "") }
    var complicacion by rememberSaveable { mutableStateOf(monitoreo?.complicacion ?: false) }
    var detalleComplicacion by rememberSaveable { mutableStateOf(monitoreo?.detalleComplicacion ?: "") }
    var cambioMotor by rememberSaveable { mutableStateOf(monitoreo?.cambioMotor ?: "") }

    val context = LocalContext.current
    val paciente = pacientes.find { it.dniPaciente.toString() == dniPaciente }
    val monitoreoVM: MonitoreoViewModel = viewModel(factory = MonitoreoViewModelFactory(Graph.monitoreoRepository))
    val monitoreos by monitoreoVM.monitoreos.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (monitoreo == null) "Nuevo Monitoreo" else "Editar Monitoreo",
                        fontFamily = FontFamily.Monospace
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onCancel) {
                        Icon(Icons.Default.Close, contentDescription = "Cancelar")
                    }
                },
                backgroundColor = colorResource(id = R.color.teal_200)
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (fechaRealizado.isBlank() || dniPaciente.isBlank()) {
                    Toast.makeText(context, "Por favor, completá la fecha de realización y el DNI del paciente.", Toast.LENGTH_SHORT).show()
                    return@FloatingActionButton
                }

                val pacienteExiste = pacientes.any { it.dniPaciente.toString() == dniPaciente }
                if (!pacienteExiste) {
                    Toast.makeText(context, "Debes agregar un paciente válido antes de guardar el monitoreo.", Toast.LENGTH_LONG).show()
                    return@FloatingActionButton
                }

                val nuevoNroRegistro = if (monitoreos.isEmpty()) 1 else (monitoreos.maxOf { it.nroRegistro } + 1)

                val nuevo = monitoreo?.copy(
                    fechaRealizado = fechaRealizado,
                    fechaPresentado = fechaPresentado.ifBlank { null },
                    fechaCobrado = fechaCobrado.ifBlank { null },
                    dniPaciente = dniPaciente.toIntOrNull() ?: 0,
                    idMedico = selectedMedicoId,
                    idTecnico = selectedTecnicoId,
                    idLugar = selectedLugarId,
                    idPatologia = selectedPatologiaId,
                    detalleAnestesia = anestesia,
                    complicacion = complicacion,
                    detalleComplicacion = detalleComplicacion,
                    cambioMotor = cambioMotor
                ) ?: Monitoreo(
                    nroRegistro = nuevoNroRegistro,
                    fechaRealizado = fechaRealizado,
                    fechaPresentado = fechaPresentado.ifBlank { null },
                    fechaCobrado = fechaCobrado.ifBlank { null },
                    dniPaciente = dniPaciente.toIntOrNull() ?: 0,
                    idMedico = selectedMedicoId,
                    idTecnico = selectedTecnicoId,
                    idLugar = selectedLugarId,
                    idPatologia = selectedPatologiaId,
                    detalleAnestesia = anestesia,
                    complicacion = complicacion,
                    detalleComplicacion = detalleComplicacion,
                    cambioMotor = cambioMotor
                )


                onSave(nuevo)
            }) {
                Icon(Icons.Default.Check, contentDescription = "Guardar")
            }
        }

    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            DateSelector("Fecha Realizado", fechaRealizado) { fechaRealizado = it }
            DateSelector("Fecha Presentado", fechaPresentado) { fechaPresentado = it }
            DateSelector("Fecha Cobrado", fechaCobrado) { fechaCobrado = it }

            Text("Paciente: ${paciente?.let { "${it.nombre} ${it.apellido} (${it.dniPaciente})" } ?: "Sin registro"}")

            Row(verticalAlignment = Alignment.CenterVertically) {
                if (paciente == null) {
                    IconButton(onClick = {
                        showDialogTipo = "Paciente"
                        camposDialog.clear()
                        camposDialog.addAll(
                            listOf(
                                "DNI" to mutableStateOf(dniPaciente),
                                "Nombre" to mutableStateOf(""),
                                "Apellido" to mutableStateOf(""),
                                "Edad" to mutableStateOf(""),
                                "Mutual" to mutableStateOf("")
                            )
                        )
                    }) {
                        Text("+ Agregar Paciente")
                    }
                } else {
                    IconButton(onClick = {
                        showDialogTipo = "Paciente"
                        camposDialog.clear()
                        camposDialog.addAll(
                            listOf(
                                "DNI" to mutableStateOf(paciente.dniPaciente.toString()),
                                "Nombre" to mutableStateOf(paciente.nombre),
                                "Apellido" to mutableStateOf(paciente.apellido),
                                "Edad" to mutableStateOf(paciente.edad.toString()),
                                "Mutual" to mutableStateOf(paciente.mutual)
                            )
                        )
                    }) {
                        Text("✏️ Editar Paciente")
                    }
                }
            }
            EntityDropdown(
                label = "Médico",
                options = medicos.map { "${it.nombre} ${it.apellido}" },
                selectedIndex = medicos.indexOfFirst { it.id == selectedMedicoId },
                onSelect = { selectedMedicoId = medicos[it].id },
                onAddClick = {
                    showDialogTipo = "Médico"
                    camposDialog.clear()
                    camposDialog.addAll(
                        listOf("Nombre" to mutableStateOf(""), "Apellido" to mutableStateOf(""))
                    )
                }
            )

            EntityDropdown(
                label = "Técnico",
                options = tecnicos.map { "${it.nombre} ${it.apellido}" },
                selectedIndex = tecnicos.indexOfFirst { it.id == selectedTecnicoId },
                onSelect = { selectedTecnicoId = tecnicos[it].id },
                onAddClick = {
                    showDialogTipo = "Técnico"
                    camposDialog.clear()
                    camposDialog.addAll(
                        listOf("Nombre" to mutableStateOf(""), "Apellido" to mutableStateOf(""))
                    )
                }
            )

            EntityDropdown(
                label = "Lugar",
                options = lugares.map { "${it.nombre}, ${it.provincia}" },
                selectedIndex = lugares.indexOfFirst { it.id == selectedLugarId },
                onSelect = { selectedLugarId = lugares[it].id },
                onAddClick = {
                    showDialogTipo = "Lugar"
                    camposDialog.clear()
                    camposDialog.addAll(
                        listOf("Nombre" to mutableStateOf(""), "Provincia" to mutableStateOf(""))
                    )
                }
            )

            EntityDropdown(
                label = "Patología",
                options = patologias.map { it.nombre },
                selectedIndex = patologias.indexOfFirst { it.id == selectedPatologiaId },
                onSelect = { selectedPatologiaId = patologias[it].id },
                onAddClick = {
                    showDialogTipo = "Patología"
                    camposDialog.clear()
                    camposDialog.add("Nombre" to mutableStateOf(""))
                }
            )

            OutlinedTextField(value = anestesia, onValueChange = { anestesia = it }, label = { Text("Detalle Anestesia") })

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Complicación")
                Switch(checked = complicacion, onCheckedChange = { complicacion = it }, colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.Red,
                    checkedTrackColor = Color.Red,
                    uncheckedThumbColor = MaterialTheme.colors.secondary,
                    uncheckedTrackColor = MaterialTheme.colors.secondaryVariant,
                ))
            }
            if (complicacion) {
                OutlinedTextField(value = detalleComplicacion, onValueChange = { detalleComplicacion = it }, label = { Text("Detalle Complicación") })
            }

            OutlinedTextField(value = cambioMotor, onValueChange = { cambioMotor = it }, label = { Text("Cambio Motor") })
        }

        if (showDialogTipo.isNotEmpty()) {
            SimpleInputDialog(
                title = if (showDialogTipo == "Paciente" && paciente != null) "Editar Paciente" else "Nuevo $showDialogTipo",
                fields = camposDialog.mapIndexed { index, pair ->
                    if (showDialogTipo == "Paciente" && index == 0 && paciente != null) {
                        // Marcar DNI como no editable
                        pair.copy(second = mutableStateOf(pair.second.value))
                    } else {
                        pair
                    }
                },
                onDismiss = { showDialogTipo = "" },
                onConfirm = {
                    when (showDialogTipo) {
                        "Paciente" -> {
                            val nuevoDni = camposDialog[0].second.value.toIntOrNull() ?: 0
                            val nuevoPaciente = Paciente(
                                dniPaciente = nuevoDni,
                                nombre = camposDialog[1].second.value,
                                apellido = camposDialog[2].second.value,
                                edad = camposDialog[3].second.value.toIntOrNull() ?: 0,
                                mutual = camposDialog[4].second.value
                            )
                            pacienteVM.addPaciente(nuevoPaciente)
                            dniPaciente = nuevoDni.toString()
                        }
                        "Médico" -> medicoVM.addMedico(camposDialog[0].second.value, camposDialog[1].second.value)
                        "Técnico" -> tecnicoVM.addTecnico(camposDialog[0].second.value, camposDialog[1].second.value)
                        "Lugar" -> lugarVM.addLugar(camposDialog[0].second.value, camposDialog[1].second.value)
                        "Patología" -> patologiaVM.addPatologia(camposDialog[0].second.value)
                    }
                    showDialogTipo = ""
                }
            )

        }
    }
}
