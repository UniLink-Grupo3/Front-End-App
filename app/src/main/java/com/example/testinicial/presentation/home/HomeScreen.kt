package com.example.testinicial.presentation.home

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.testinicial.R
import com.example.testinicial.data.ReservedTrip
import com.example.testinicial.data.SharedTripState
import com.example.testinicial.domain.model.Vehicle
import androidx.core.net.toUri

val BlueMetallic = Color(0xFF5874B0)
val OrangeAccent = Color(0xFFF57C00)
val BackgroundLight = Color(0xFFF3F4F6)
val CardWhite = Color(0xFFFFFFFF)
val TextGray = Color(0xFF555555)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onNavigateToProfile: () -> Unit) {
    val context = LocalContext.current

    val vehicles = listOf(
        Vehicle("Toyota", "Corolla", "2020", "UPC Monterrico", painterResource(id = R.drawable.vehicle1)),
        Vehicle("Honda", "Civic", "2021", "UPC San Miguel", painterResource(id = R.drawable.vehicle2)),
        Vehicle("Ford", "Fiesta", "2019", "UPC San Isidro", painterResource(id = R.drawable.vehicle3)),
        Vehicle("Kia", "Rio", "2022", "UPC Monterrico", painterResource(id = R.drawable.vehicle1)),
        Vehicle("Chevrolet", "Onix", "2021", "UPC San Miguel", painterResource(id = R.drawable.vehicle2)),
        Vehicle("Hyundai", "Elantra", "2020", "UPC San Isidro", painterResource(id = R.drawable.vehicle3)),
        Vehicle("Mazda", "3", "2022", "UPC Monterrico", painterResource(id = R.drawable.vehicle1)),
        Vehicle("Nissan", "Sentra", "2023", "UPC San Miguel", painterResource(id = R.drawable.vehicle2)),
        Vehicle("Volkswagen", "Jetta", "2021", "UPC San Isidro", painterResource(id = R.drawable.vehicle3)),
        Vehicle("Renault", "Logan", "2020", "UPC Monterrico", painterResource(id = R.drawable.vehicle1))
    )

    var selectedVehicle by remember { mutableStateOf<Vehicle?>(null) }

    Scaffold(
        containerColor = Color(0xFFF8F9FA),
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "Logo",
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "RideUp",
                            color = Color.White,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                actions = {
                    TextButton(onClick = {
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            "https://unilink-grupo3.github.io/RideUp-Landing-Page/".toUri()
                        )
                        context.startActivity(intent)
                    }) {
                        Text("Conócenos", color = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BlueMetallic,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        },
        bottomBar = {
            NavigationBar(containerColor = BlueMetallic) {
                NavigationBarItem(
                    selected = false,
                    onClick = onNavigateToProfile,
                    icon = {
                        Icon(Icons.Default.Person, contentDescription = "Perfil", tint = Color.White)
                    },
                    label = { Text("Perfil", color = Color.White) }
                )
            }
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentPadding = PaddingValues(bottom = 100.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // BIENVENIDA
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(BlueMetallic)
                        .padding(vertical = 32.dp, horizontal = 20.dp)
                ) {
                    Column {
                        Text(
                            "¡Bienvenido a RideUp!",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                color = Color.White,
                                fontWeight = FontWeight.ExtraBold
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "Comparte viajes, ahorra tiempo y dinero.",
                            style = MaterialTheme.typography.bodyLarge.copy(color = Color.White)
                        )
                    }
                }
            }

            item {
                Image(
                    painter = painterResource(id = R.drawable.banner),
                    contentDescription = "Banner promocional",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.medium)
                        .padding(horizontal = 20.dp).clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.FillWidth
                )
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    FeatureItem(icon = R.drawable.ic_safe, label = "Seguro")
                    FeatureItem(icon = R.drawable.ic_fast, label = "Rápido")
                    FeatureItem(icon = R.drawable.ic_affordable, label = "Económico")
                }
            }

            item {
                Text(
                    "Vehículos disponibles",
                    modifier = Modifier
                        .padding(start = 20.dp, top = 24.dp, bottom = 4.dp),
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = BlueMetallic
                )
            }

            items(vehicles) { vehicle ->
                VehicleCard(vehicle = vehicle) {
                    selectedVehicle = it
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
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(vehicle) },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = vehicle.image,
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(MaterialTheme.shapes.medium)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text("${vehicle.name} ${vehicle.model}", fontWeight = FontWeight.Bold, color = BlueMetallic)
                Text("Destino: ${vehicle.destination}", color = TextGray)
                Text("Año: ${vehicle.year}", color = TextGray)
            }
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = OrangeAccent
            )
        }
    }
}

@Composable
fun FeatureItem(icon: Int, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Card(
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(4.dp),
            modifier = Modifier.size(80.dp)
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = label,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(label, color = TextGray, style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
fun ReservationDialog(vehicle: Vehicle, onDismiss: () -> Unit) {
    var confirmSuccess by remember { mutableStateOf(false) }

    if (confirmSuccess) {
        AlertDialog(
            onDismissRequest = { confirmSuccess = false; onDismiss() },
            title = { Text("Reserva exitosa", fontWeight = FontWeight.Bold) },
            text = { Text("Has reservado el ${vehicle.name} ${vehicle.model}. ¡Buen viaje!") },
            confirmButton = {
                FilledTonalButton(onClick = { confirmSuccess = false; onDismiss() }) {
                    Text("Aceptar")
                }
            }
        )
    } else {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = vehicle.image,
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(MaterialTheme.shapes.small)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("${vehicle.name} ${vehicle.model}", fontWeight = FontWeight.Bold)
                }
            },
            text = {
                Column {
                    Text("Destino: ${vehicle.destination}")
                    Text("Año: ${vehicle.year}")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Tarifa estimada: S/ 8.00",
                        color = OrangeAccent,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            },
            confirmButton = {
                FilledTonalButton(onClick = {
                    SharedTripState.reservedTrip.value = ReservedTrip(
                        vehicleImage = vehicle.image,
                        destination = vehicle.destination,
                        vehicleName = vehicle.name,
                        vehicleModel = vehicle.model,
                        vehicleYear = vehicle.year
                    )
                    confirmSuccess = true
                }) {
                    Text("Confirmar")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Cancelar")
                }
            }
        )
    }
}
