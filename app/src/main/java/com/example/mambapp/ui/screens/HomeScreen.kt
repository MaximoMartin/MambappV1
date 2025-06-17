package com.example.mambapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material.ListItem
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mambapp.data.entities.Monitoreo
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.mambapp.R


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    monitoreos: List<Monitoreo>,
    onMonitoreoClick: (Int) -> Unit,
    onAddClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(
                "Lista de Monitoreos",
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            ) }, backgroundColor= colorResource(id = R.color.teal_200))
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick, containerColor=colorResource(id = R.color.teal_200)) {
                Icon(Icons.Default.Add, contentDescription = "Nuevo Monitoreo")
            }
        }
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            items(monitoreos.sortedByDescending { it.fechaRealizado }) { monitoreo ->
                ListItem(
                    text = { Text("Registro #${monitoreo.nroRegistro}") },
                    secondaryText = { Text(monitoreo.fechaRealizado) },
                    modifier = Modifier
                        .clickable { onMonitoreoClick(monitoreo.nroRegistro) }
                        .fillMaxWidth()
                        .padding(8.dp)
                )
                Divider()
            }
        }
    }


}