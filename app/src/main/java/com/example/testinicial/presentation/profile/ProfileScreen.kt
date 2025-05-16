package com.example.testinicial.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.testinicial.R
import com.example.testinicial.data.SharedTripState

@Composable
fun ProfileScreen() {
    val reservedTrip = SharedTripState.reservedTrip.value
    var showPaymentDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .padding(16.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Image(
                painter = painterResource(id = R.drawable.profile_icon),
                contentDescription = "Foto de perfil",
                modifier = Modifier.fillMaxSize()
            )
            Icon(
                imageVector = Icons.Default.AddAPhoto,
                contentDescription = "Subir imagen",
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { /* referencial */ }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF1E1E1E),
                contentColor = Color.White
            ),
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Nombre de usuario: usertest6")
                Text(text = "Correo: usertest6@gmail.com")
                Text(text = "Teléfono: +51 965965965")
                Text(text = "Descripción: Amante de los autos y los viajes compartidos.")
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { /* referencial */ }) {
                    Icon(Icons.Default.AddAPhoto, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Subir publicación")
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        reservedTrip?.let { trip ->
            Text(
                text = "Viaje en curso",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF2C2C2C),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .wrapContentWidth()
                    .align(Alignment.CenterHorizontally),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = trip.vehicleImage,
                        contentDescription = null,
                        modifier = Modifier.size(140.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(text = "Vehículo: ${trip.vehicleName}")
                    Text(text = "Modelo: ${trip.vehicleModel}")
                    Text(text = "Año: ${trip.vehicleYear}")
                    Text(text = "Destino: ${trip.destination}")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(onClick = { showPaymentDialog = true }) {
                    Text("Pagar y confirmar viaje finalizado")
                }
            }
        }

        if (showPaymentDialog) {
            AlertDialog(
                onDismissRequest = { showPaymentDialog = false },
                title = { Text("Pago aceptado") },
                text = { Text("Tu viaje ha sido confirmado exitosamente.") },
                confirmButton = {
                    TextButton(onClick = {
                        showPaymentDialog = false
                        SharedTripState.reservedTrip.value = null
                    }) {
                        Text("Aceptar")
                    }
                }
            )
        }
    }
}


