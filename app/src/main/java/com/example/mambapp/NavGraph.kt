package com.example.mambapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mambapp.ui.screens.DetailScreen
import com.example.mambapp.ui.screens.FormScreen
import com.example.mambapp.ui.screens.HomeScreen
import com.example.mambapp.viewmodel.MonitoreoViewModel

@Composable
fun MambappNavGraph(navController: NavHostController, viewModel: MonitoreoViewModel) {
    NavHost(navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                monitoreos = viewModel.monitoreos.collectAsState().value,
                onMonitoreoClick = { navController.navigate("detail/$it") },
                onAddClick = { navController.navigate("form") }
            )
        }
        composable("detail/{id}", arguments = listOf(navArgument("id") { type = NavType.IntType })) {
            val id = it.arguments?.getInt("id") ?: 0
            val monitoreo = viewModel.getMonitoreoByNro(id)
            DetailScreen(
                monitoreo = monitoreo,
                onBack = { navController.popBackStack() },
                onEdit = { navController.navigate("form/$id") },
                onDelete = {
                    viewModel.deleteMonitoreoByRegistro(id)
                    navController.popBackStack()
                }
            )
        }
        composable("form", arguments = listOf()) {
            FormScreen(monitoreo = null, onSave = {
                viewModel.addMonitoreo(it)
                navController.popBackStack()
            }, onCancel = { navController.popBackStack() })
        }
        composable("form/{id}", arguments = listOf(navArgument("id") { type = NavType.IntType })) {
            val id = it.arguments?.getInt("id") ?: 0
            val monitoreo = viewModel.getMonitoreoByNro(id)
            FormScreen(monitoreo = monitoreo, onSave = {
                viewModel.updateMonitoreo(it)
                navController.popBackStack()
            }, onCancel = { navController.popBackStack() })
        }
    }
}
