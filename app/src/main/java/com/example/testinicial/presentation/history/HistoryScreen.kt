package com.example.testinicial.presentation.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.testinicial.data.SharedHistoryState

val BlueMetallic = Color(0xFF3A5A98)
val SurfaceWhite = Color.White

data class TripHistory(
    val origin: String,
    val destination: String,
    val date: String,
    val status: String
)

@Composable
fun HistoryScreen() {
    val trips = SharedHistoryState.tripHistoryList

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F6F9))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Historial de Viajes",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            color = BlueMetallic
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (trips.isEmpty()) {
            Text(
                "No tienes viajes completados aún.",
                color = BlueMetallic
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(trips) { trip ->
                    HistoryCard(trip)
                }
            }
        }
    }
}

@Composable
fun HistoryCard(trip: TripHistory) {
    Card(
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.fillMaxWidth(0.9f)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Origen: ${trip.origin}", fontWeight = FontWeight.Bold)
            Text("Destino: ${trip.destination}")
            Text("Fecha: ${trip.date}")
            Text("Estado: ${trip.status}")
        }
    }
}
