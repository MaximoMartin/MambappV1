package com.example.mambapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@Composable
fun SimpleInputDialog(
    title: String,
    fields: List<Pair<String, MutableState<String>>>,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            Column {
                fields.forEach { (label, state) ->
                    OutlinedTextField(
                        value = state.value,
                        onValueChange = {
                            if (!(title.contains("Editar Paciente") && label == "DNI")) {
                                state.value = it
                            }
                        },
                        label = { Text(label) },
                        enabled = !(title.contains("Editar Paciente") && label == "DNI")
                    )
                }
            }
        },
        confirmButton = {
            Button(onClick = onConfirm) { Text("Guardar") }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}