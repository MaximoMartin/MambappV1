// FormScreen.kt
package com.example.mambapp.ui.screens

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mambapp.Graph
import com.example.mambapp.R
import com.example.mambapp.data.entities.Monitoreo
import com.example.mambapp.data.entities.Paciente
import com.example.mambapp.ui.components.*
import com.example.mambapp.viewmodel.*
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FormScreen(
    monitoreo: Monitoreo?,
    onSave: (Monitoreo) -> Unit,
    onCancel: () -> Unit
) {
    val context = LocalContext.current
    val keyboard = LocalSoftwareKeyboardController.current
    val hoy = LocalDate.now().toString()

    val pacienteVM: PacienteViewModel = viewModel(factory = PacienteViewModelFactory(Graph.pacienteRepository))
    val pacientes by pacienteVM.pacientes.collectAsState()

    val medicoVM: MedicoViewModel = viewModel(factory = MedicoViewModelFactory(Graph.medicoRepository))
    val tecnicoVM: TecnicoViewModel = viewModel(factory = TecnicoViewModelFactory(Graph.tecnicoRepository))
    val lugarVM: LugarViewModel = viewModel(factory = LugarViewModelFactory(Graph.lugarRepository))
    val patologiaVM: PatologiaViewModel = viewModel(factory = PatologiaViewModelFactory(Graph.patologiaRepository))
    val monitoreoVM: MonitoreoViewModel = viewModel(factory = MonitoreoViewModelFactory(Graph.monitoreoRepository))
    val monitoreos by monitoreoVM.monitoreos.collectAsState()

    val medicos by medicoVM.medicos.collectAsState()
    val tecnicos by tecnicoVM.tecnicos.collectAsState()
    val lugares by lugarVM.lugares.collectAsState()
    val patologias by patologiaVM.patologias.collectAsState()

    var fechaRealizado by rememberSaveable { mutableStateOf(monitoreo?.fechaRealizado ?: hoy) }
    var fechaPresentado by rememberSaveable { mutableStateOf(monitoreo?.fechaPresentado ?: "") }
    var fechaCobrado by rememberSaveable { mutableStateOf(monitoreo?.fechaCobrado ?: "") }

    var dniPaciente by rememberSaveable { mutableStateOf(monitoreo?.dniPaciente?.toString() ?: "") }
    val paciente = pacientes.find { it.dniPaciente.toString() == dniPaciente }

    var selectedMedicoId by rememberSaveable { mutableStateOf(monitoreo?.idMedico ?: -1) }
    var selectedTecnicoId by rememberSaveable { mutableStateOf(monitoreo?.idTecnico ?: -1) }
    var selectedLugarId by rememberSaveable { mutableStateOf(monitoreo?.idLugar ?: -1) }
    var selectedPatologiaId by rememberSaveable { mutableStateOf(monitoreo?.idPatologia ?: -1) }

    var anestesia by rememberSaveable { mutableStateOf(monitoreo?.detalleAnestesia ?: "") }
    var complicacion by rememberSaveable { mutableStateOf(monitoreo?.complicacion ?: false) }
    var detalleComplicacion by rememberSaveable { mutableStateOf(monitoreo?.detalleComplicacion ?: "") }
    var cambioMotor by rememberSaveable { mutableStateOf(monitoreo?.cambioMotor ?: "") }

    var showDialogTipo by rememberSaveable { mutableStateOf("") }
    var camposDialog = remember { mutableStateListOf<Pair<String, MutableState<String>>>() }

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
                val fechaHoy = LocalDate.now()
                val fechaValida = try {
                    !LocalDate.parse(fechaRealizado).isAfter(fechaHoy) &&
                            (fechaPresentado.isBlank() || !LocalDate.parse(fechaPresentado).isAfter(fechaHoy)) &&
                            (fechaCobrado.isBlank() || !LocalDate.parse(fechaCobrado).isAfter(fechaHoy))
                } catch (_: Exception) { false }

                if (!fechaValida || dniPaciente.isBlank()) {
                    Toast.makeText(context, "Fechas inválidas o DNI vacío. No se puede guardar.", Toast.LENGTH_SHORT).show()
                    return@FloatingActionButton
                }

                val pacienteExiste = pacientes.any { it.dniPaciente.toString() == dniPaciente }
                if (!pacienteExiste) {
                    Toast.makeText(context, "Debes agregar un paciente válido antes de guardar.", Toast.LENGTH_LONG).show()
                    return@FloatingActionButton
                }

                val nuevoNroRegistro = monitoreos.maxOfOrNull { it.nroRegistro }?.plus(1) ?: 1

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

                monitoreoVM.addMonitoreo(nuevo)
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
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            CardSection("🗓️ Fechas") {
                DateSelector("Fecha Realizado", fechaRealizado) { fechaRealizado = it }
                DateSelector("Fecha Presentado", fechaPresentado) { fechaPresentado = it }
                DateSelector("Fecha Cobrado", fechaCobrado) { fechaCobrado = it }
            }

            CardSection("👤 Paciente") {
                NumericInputField("DNI del Paciente", dniPaciente) {
                    dniPaciente = it
                    keyboard?.hide()
                }
                PacienteControl(
                    paciente = paciente,
                    onAgregar = {
                        showDialogTipo = "Paciente"
                        camposDialog.setFromNames("DNI", "Nombre", "Apellido", "Edad", "Mutual")
                        camposDialog[0].second.value = dniPaciente
                    },
                    onEditar = {
                        showDialogTipo = "Paciente"
                        camposDialog.setFromNames("DNI", "Nombre", "Apellido", "Edad", "Mutual")
                        camposDialog[0].second.value = paciente?.dniPaciente?.toString() ?: ""
                        camposDialog[1].second.value = paciente?.nombre ?: ""
                        camposDialog[2].second.value = paciente?.apellido ?: ""
                        camposDialog[3].second.value = paciente?.edad?.toString() ?: ""
                        camposDialog[4].second.value = paciente?.mutual ?: ""
                    }
                )
            }

            CardSection("👨‍⚕️ Profesionales") {
                EntityDropdown("Médico", medicos.map { "${it.nombre} ${it.apellido}" }, medicos.indexOfFirst { it.id == selectedMedicoId }, { selectedMedicoId = medicos[it].id }) {
                    showDialogTipo = "Médico"
                    camposDialog.setFromNames("Nombre", "Apellido")
                }
                EntityDropdown("Técnico", tecnicos.map { "${it.nombre} ${it.apellido}" }, tecnicos.indexOfFirst { it.id == selectedTecnicoId }, { selectedTecnicoId = tecnicos[it].id }) {
                    showDialogTipo = "Técnico"
                    camposDialog.setFromNames("Nombre", "Apellido")
                }
            }

            CardSection("📍 Ubicación") {
                EntityDropdown("Lugar", lugares.map { "${it.nombre}, ${it.provincia}" }, lugares.indexOfFirst { it.id == selectedLugarId }, { selectedLugarId = lugares[it].id }) {
                    showDialogTipo = "Lugar"
                    camposDialog.setFromNames("Nombre", "Provincia")
                }
            }

            CardSection("🧬 Información Clínica") {
                EntityDropdown("Patología", patologias.map { it.nombre }, patologias.indexOfFirst { it.id == selectedPatologiaId }, { selectedPatologiaId = patologias[it].id }) {
                    showDialogTipo = "Patología"
                    camposDialog.setFromNames("Nombre")
                }
                OutlinedTextField(value = anestesia, onValueChange = { anestesia = it }, label = { Text("Detalle Anestesia") }, modifier = Modifier.fillMaxWidth())
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Complicación")
                    Switch(checked = complicacion, onCheckedChange = { complicacion = it })
                }
                if (complicacion) {
                    OutlinedTextField(value = detalleComplicacion, onValueChange = { detalleComplicacion = it }, label = { Text("Detalle Complicación") }, modifier = Modifier.fillMaxWidth())
                }
                OutlinedTextField(value = cambioMotor, onValueChange = { cambioMotor = it }, label = { Text("Cambio Motor") }, modifier = Modifier.fillMaxWidth())
            }
        }

        if (showDialogTipo.isNotEmpty()) {
            SimpleInputDialog(
                title = "Nuevo $showDialogTipo",
                fields = camposDialog,
                onDismiss = { showDialogTipo = "" },
                onConfirm = {
                    when (showDialogTipo) {
                        "Paciente" -> {
                            val nuevoDni = camposDialog[0].second.value.toIntOrNull() ?: 0
                            pacienteVM.addPaciente(
                                Paciente(
                                    dniPaciente = nuevoDni,
                                    nombre = camposDialog[1].second.value,
                                    apellido = camposDialog[2].second.value,
                                    edad = camposDialog[3].second.value.toIntOrNull() ?: 0,
                                    mutual = camposDialog[4].second.value
                                )
                            )
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

@Composable
fun CardSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Card(elevation = 6.dp, modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            SectionHeader(title)
            Spacer(Modifier.height(8.dp))
            content()
        }
    }
}
