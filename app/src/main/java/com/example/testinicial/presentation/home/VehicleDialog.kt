package com.example.testinicial.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.*

@Composable
fun VehicleDialog(vehicle: Vehicle) {
    var open by remember { mutableStateOf(true) }

    if (open) {
        AlertDialog(
            onDismissRequest = { open = false },
            title = { Text("${vehicle.name} ${vehicle.model}") },
            text = {
                Column {
                    Text("Destino: ${vehicle.destination}")
                    Text("Año: ${vehicle.year}")
                    Text("Tarifa estimada: $8.000")
                }
            },
            confirmButton = {
                TextButton(onClick = { open = false }) {
                    Text("Confirmar reserva")
                }
            },
            dismissButton = {
                TextButton(onClick = { open = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}
