package com.example.mambapp.ui.components

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList

fun SnapshotStateList<Pair<String, MutableState<String>>>.setFromNames(vararg fieldNames: String) {
    clear()
    fieldNames.forEach { name ->
        add(name to mutableStateOf(""))
    }
}
