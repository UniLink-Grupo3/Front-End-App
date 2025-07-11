package com.example.testinicial.presentation.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.example.testinicial.R
import com.example.testinicial.data.ReservedTrip
import com.example.testinicial.data.SharedTripState
import com.example.testinicial.domain.model.Vehicle
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.testinicial.data.remote.RetrofitInstance
data class UniversityStudentWithoutCar(
    val id: Int,
    val name: String,
    val code: String,
    val university: String,
    val image: String = "default_image",
    val numberPeople: Int,
    val destination: String,
    val pickup: String,
    val price: Int,
    val latitude: Double,
    val longitude: Double
)

val BlueMetallic = Color(0xFF5874B0)
val OrangeAccent = Color(0xFFF57C00)
val TextGray = Color(0xFF555555)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onNavigateToProfile: () -> Unit, onNavigateToSupport: () -> Unit) {
    val context = LocalContext.current

    val vehicles = listOf(
        Vehicle("Toyota", "Corolla", "2020", "UPC Monterrico", painterResource(id = R.drawable.vehicle1)),
        Vehicle("Honda", "Civic", "2021", "UPC San Miguel", painterResource(id = R.drawable.vehicle2)),
        Vehicle("Ford", "Fiesta", "2019", "UPC San Isidro", painterResource(id = R.drawable.vehicle3))
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
                    TextButton(onClick = { onNavigateToSupport() }) {
                        Text("Soporte", color = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BlueMetallic,
                    titleContentColor = Color.White
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
                    contentDescription = "Banner",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .clip(MaterialTheme.shapes.medium)
                )
            }

            item {
                Text(
                    "Vehículos disponibles",
                    modifier = Modifier.padding(start = 20.dp, top = 24.dp, bottom = 4.dp),
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = BlueMetallic
                )
            }

            items(vehicles) { vehicle ->
                VehicleCard(vehicle) { selectedVehicle = it }
            }
        }

        selectedVehicle?.let {
            ReservationDialog(vehicle = it) { selectedVehicle = null }
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
            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = OrangeAccent)
        }
    }
}

@Composable
fun ReservationDialog(vehicle: Vehicle, onDismiss: () -> Unit) {
    val context = LocalContext.current

    var confirmSuccess by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var code by remember { mutableStateOf("") }
    var university by remember { mutableStateOf("") }
    var pickup by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var numberPeople by remember { mutableStateOf("") }
    var latitude by remember { mutableStateOf<Double?>(null) }
    var longitude by remember { mutableStateOf<Double?>(null) }
    var useCurrentLocation by remember { mutableStateOf<Boolean?>(null) }

    val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            if (granted) {
                fusedLocationClient.lastLocation.addOnSuccessListener {
                    it?.let { location ->
                        latitude = location.latitude
                        longitude = location.longitude
                    }
                }
            } else {
                useCurrentLocation = false
            }
        }
    )

    if (useCurrentLocation == null) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Ubicación") },
            text = { Text("¿Usar tu ubicación actual?") },
            confirmButton = {
                TextButton(onClick = {
                    useCurrentLocation = true
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                    } else {
                        fusedLocationClient.lastLocation.addOnSuccessListener {
                            it?.let { location ->
                                latitude = location.latitude
                                longitude = location.longitude
                            }
                        }
                    }
                }) { Text("Sí") }
            },
            dismissButton = {
                TextButton(onClick = { useCurrentLocation = false }) { Text("No") }
            }
        )
        return
    }

    if (confirmSuccess) {
        AlertDialog(
            onDismissRequest = { confirmSuccess = false; onDismiss() },
            title = { Text("¡Solicitud enviada!") },
            confirmButton = {
                FilledTonalButton(onClick = { confirmSuccess = false; onDismiss() }) {
                    Text("Aceptar")
                }
            }
        )
    } else {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Datos del viaje") },
            text = {
                Column {
                    OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nombre") })
                    OutlinedTextField(value = code, onValueChange = { code = it }, label = { Text("Código") })
                    OutlinedTextField(value = university, onValueChange = { university = it }, label = { Text("Universidad") })
                    OutlinedTextField(value = pickup, onValueChange = { pickup = it }, label = { Text("Recojo") })
                    OutlinedTextField(value = price, onValueChange = { price = it }, label = { Text("Precio") })
                    OutlinedTextField(value = numberPeople, onValueChange = { numberPeople = it }, label = { Text("Personas") })
                    if (useCurrentLocation == false) {
                        OutlinedTextField(
                            value = latitude?.toString() ?: "",
                            onValueChange = { latitude = it.toDoubleOrNull() },
                            label = { Text("Latitud") }
                        )
                        OutlinedTextField(
                            value = longitude?.toString() ?: "",
                            onValueChange = { longitude = it.toDoubleOrNull() },
                            label = { Text("Longitud") }
                        )
                    }
                }
            },

            confirmButton = {
                FilledTonalButton(onClick = {
                    if (latitude != null && longitude != null) {
                        val student = UniversityStudentWithoutCar(
                            id = 0,
                            name = name,
                            code = code,
                            university = university,
                            numberPeople = numberPeople.toIntOrNull() ?: 1,
                            destination = vehicle.destination,
                            pickup = pickup,
                            price = price.toIntOrNull() ?: 0,
                            latitude = latitude!!,
                            longitude = longitude!!
                        )

                        CoroutineScope(Dispatchers.IO).launch {
                            try {
                                RetrofitInstance.studentApi.postStudent(student)
                                println("🚗 Enviado correctamente: $student")
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                        SharedTripState.reservedTrip.value = ReservedTrip(
                            vehicleName = vehicle.name,
                            vehicleYear = vehicle.year,
                            vehicleModel = vehicle.model,
                            destination = vehicle.destination,
                            vehicleImage = vehicle.image
                        )
                        confirmSuccess = true
                    }
                }) { Text("Enviar") }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) { Text("Cancelar") }
            }
        )
    }
}