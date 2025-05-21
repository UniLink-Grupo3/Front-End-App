package com.example.testinicial.presentation.home

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.FontScaling
import androidx.compose.ui.unit.dp
import com.example.testinicial.R
import com.example.testinicial.domain.model.Vehicle
import androidx.core.net.toUri
import com.example.testinicial.data.ReservedTrip
import com.example.testinicial.data.SharedTripState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onNavigateToProfile: () -> Unit) {
    val context = LocalContext.current

    val vehicles = listOf(
        Vehicle("Toyota", "Corolla", "2020", "UPC Monterrico", painterResource(id = R.drawable.vehicle1)),
        Vehicle("Honda", "Civic", "2021", "UPC San Miguel", painterResource(id = R.drawable.vehicle2)),
        Vehicle("Ford", "Fiesta", "2019", "UPC San Isidro", painterResource(id = R.drawable.vehicle3))
    )

    var selectedVehicle by remember { mutableStateOf<Vehicle?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.backgroundmain),
            contentDescription = "Fondo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xCC000000))
        )
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xC591D2FF),
                        titleContentColor = Color.White
                    ),
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.logo),
                                contentDescription = "Logo",
                                modifier = Modifier
                                    .size(32.dp)
                                    .padding(end = 8.dp)
                            )
                            Text(
                                text = "RideUp",
                                style = MaterialTheme.typography.titleLarge,
                                color = Color.White
                            )
                        }
                    },
                    actions = {
                        TextButton(onClick = {
                            val intent = Intent(Intent.ACTION_VIEW, "https://unilink-grupo3.github.io/RideUp-Landing-Page/".toUri())
                            context.startActivity(intent)
                        }) {
                            Text("Conócenos", color = Color.White)
                        }
                    }
                )
            },
            bottomBar = {
                BottomAppBar(
                    containerColor = Color(0xC591D2FF),
                    contentColor = Color.White
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = onNavigateToProfile) {
                        Icon(Icons.Default.Person, contentDescription = "Perfil", tint = Color.White)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        ) { padding ->
            Column(modifier = Modifier.padding(padding)) {
                Text(
                    text = "Vehículos disponibles",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.ExtraBold,
                    ),
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                )
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(vehicles) { vehicle ->
                        VehicleCard(vehicle = vehicle, onClick = { selectedVehicle = it })
                    }
                }
            }
        }

        selectedVehicle?.let {
            ReservationDialog(vehicle = it, onDismiss = { selectedVehicle = null })
        }
    }
}

@Composable
fun VehicleCard(vehicle: Vehicle, onClick: (Vehicle) -> Unit) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(0xE5FFFFFF),
            contentColor = Color.Black
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable { onClick(vehicle) },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = vehicle.image,
                contentDescription = null,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(vehicle.name)
                Text(vehicle.model)
                Text(vehicle.destination)
            }
        }
    }
}

@Composable
fun ReservationDialog(vehicle: Vehicle, onDismiss: () -> Unit) {
    var showSuccessDialog by remember { mutableStateOf(false) }

    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { showSuccessDialog = false; onDismiss() },
            title = { Text("Reserva exitosa") },
            text = { Text("Has reservado el vehículo satisfactoriamente.") },
            confirmButton = {
                TextButton(onClick = {
                    showSuccessDialog = false
                    onDismiss()
                }) {
                    Text("Aceptar")
                }
            }
        )
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                SharedTripState.reservedTrip.value = ReservedTrip(
                    vehicleImage = vehicle.image,
                    destination = vehicle.destination,
                    vehicleName = vehicle.name,
                    vehicleModel = vehicle.model,
                    vehicleYear = vehicle.year
                )
                showSuccessDialog = true
            }) {
                Text("Confirmar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        },
        title = {
            Text("${vehicle.name} ${vehicle.model}")
        },
        text = {
            Column {
                Image(
                    painter = vehicle.image,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("Destino: ${vehicle.destination}")
                Text("Año: ${vehicle.year}")
                Text("Tarifa estimada: $8.000")
            }
        }
    )
}

