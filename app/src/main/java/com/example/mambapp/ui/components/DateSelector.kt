package com.example.mambapp.ui.components

import android.app.DatePickerDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DateSelector(
    label: String,
    date: String,
    onDateSelected: (String) -> Unit
) {
    val context = LocalContext.current
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    Column(modifier = Modifier.padding(top = 8.dp)) {
        Text("$label: $date")
        Button(onClick = {
            val parts = date.split("-").mapNotNull { it.toIntOrNull() }
            val calendar = Calendar.getInstance()
            val year = parts.getOrNull(0) ?: calendar.get(Calendar.YEAR)
            val month = (parts.getOrNull(1) ?: calendar.get(Calendar.MONTH + 1)) - 1
            val day = parts.getOrNull(2) ?: calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(
                context,
                { _, y, m, d ->
                    calendar.set(y, m, d)
                    onDateSelected(dateFormat.format(calendar.time))
                },
                year, month, day
            ).show()
        }) {
            Text("Seleccionar $label")
        }
    }
}