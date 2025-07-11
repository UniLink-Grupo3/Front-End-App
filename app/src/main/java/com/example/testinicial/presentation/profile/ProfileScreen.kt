package com.example.testinicial.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.testinicial.R
import com.example.testinicial.data.SharedTripState
import com.example.testinicial.data.SharedHistoryState
import com.example.testinicial.data.SharedPaymentState
import com.example.testinicial.presentation.history.TripHistory
import java.text.SimpleDateFormat
import java.util.*

val BlueMetallic = Color(0xFF3A5A98)
val OrangeAccent = Color(0xFFF57C00)
val BackgroundGray = Color(0xFFF4F6F9)
val TextDark = Color(0xFF333333)
val SurfaceWhite = Color.White

data class PaymentMethod(
    val cardHolder: String,
    val cardNumber: String,
    val expiryDate: String
)

@Composable
fun ProfileScreen(
    onNavigateToHistory: () -> Unit
) {
    val reservedTrip = SharedTripState.reservedTrip.value
    var showPaymentDialog by remember { mutableStateOf(false) }

    val paymentMethods = SharedPaymentState.paymentMethods
    var showAddPaymentForm by remember { mutableStateOf(false) }

    var cardHolder by remember { mutableStateOf("") }
    var cardNumber by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(130.dp)
                .padding(top = 24.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Image(
                painter = painterResource(id = R.drawable.profile_icon),
                contentDescription = "Foto de perfil",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(MaterialTheme.shapes.large)
            )
            Icon(
                imageVector = Icons.Default.AddAPhoto,
                contentDescription = "Cambiar foto",
                tint = OrangeAccent,
                modifier = Modifier
                    .size(28.dp)
                    .clickable { /* Acción referencial */ }
                    .background(Color.White, shape = MaterialTheme.shapes.small)
                    .padding(4.dp)
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Surface(
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 3.dp,
            color = SurfaceWhite,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("Nombre de usuario:", fontWeight = FontWeight.Bold, color = TextDark)
                Text("usertest8", color = TextDark)
                Spacer(modifier = Modifier.height(8.dp))

                Text("Correo:", fontWeight = FontWeight.Bold, color = TextDark)
                Text("usertest8@gmail.com", color = TextDark)
                Spacer(modifier = Modifier.height(8.dp))

                Text("Teléfono:", fontWeight = FontWeight.Bold, color = TextDark)
                Text("+51 888888888", color = TextDark)
                Spacer(modifier = Modifier.height(8.dp))

                Text("Descripción:", fontWeight = FontWeight.Bold, color = TextDark)
                Text("Amante de los autos y los viajes compartidos.", color = TextDark)
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(containerColor = OrangeAccent),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Icon(Icons.Default.AddAPhoto, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Subir publicación")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { onNavigateToHistory() },
                    colors = ButtonDefaults.buttonColors(containerColor = BlueMetallic),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Icon(Icons.Default.History, contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Ver historial", color = Color.White)
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        if (paymentMethods.isNotEmpty()) {
            Text(
                "Métodos de pago guardados:",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = BlueMetallic
            )
            Spacer(modifier = Modifier.height(8.dp))
            paymentMethods.forEachIndexed { index, method ->
                Card(
                    shape = MaterialTheme.shapes.medium,
                    elevation = CardDefaults.cardElevation(2.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.CreditCard, contentDescription = null, tint = BlueMetallic)
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text("Titular: ${method.cardHolder}", color = TextDark)
                                Text("**** **** **** ${method.cardNumber.takeLast(4)}", color = TextDark)
                                Text("Expira: ${method.expiryDate}", color = TextDark)
                            }
                        }
                        IconButton(onClick = {
                            SharedPaymentState.paymentMethods.removeAt(index)
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Red)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        Button(
            onClick = { showAddPaymentForm = !showAddPaymentForm },
            colors = ButtonDefaults.buttonColors(containerColor = OrangeAccent)
        ) {
            Icon(Icons.Default.Add, contentDescription = null, tint = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Text(if (showAddPaymentForm) "Cancelar" else "Añadir método de pago", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (showAddPaymentForm) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                tonalElevation = 3.dp,
                color = SurfaceWhite,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    OutlinedTextField(
                        value = cardHolder,
                        onValueChange = { cardHolder = it },
                        label = { Text("Titular de la tarjeta") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = cardNumber,
                        onValueChange = { cardNumber = it },
                        label = { Text("Número de tarjeta") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = expiryDate,
                        onValueChange = { expiryDate = it },
                        label = { Text("Fecha de expiración (MM/AA)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = cvv,
                        onValueChange = { cvv = it },
                        label = { Text("CVV") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            if (cardHolder.isNotBlank() && cardNumber.isNotBlank() && expiryDate.isNotBlank() && cvv.isNotBlank()) {
                                SharedPaymentState.paymentMethods.add(
                                    PaymentMethod(cardHolder, cardNumber, expiryDate)
                                )
                                cardHolder = ""
                                cardNumber = ""
                                expiryDate = ""
                                cvv = ""
                                showAddPaymentForm = false
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = BlueMetallic),
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Guardar", color = Color.White)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        reservedTrip?.let { trip ->
            Text(
                "Viaje en curso",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = BlueMetallic,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Surface(
                shape = MaterialTheme.shapes.medium,
                tonalElevation = 3.dp,
                color = SurfaceWhite,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = trip.vehicleImage,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                            .clip(MaterialTheme.shapes.medium)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Vehículo: ${trip.vehicleName}", color = TextDark)
                    Text("Modelo: ${trip.vehicleModel}", color = TextDark)
                    Text("Año: ${trip.vehicleYear}", color = TextDark)
                    Text("Destino: ${trip.destination}", color = TextDark)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { showPaymentDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = BlueMetallic),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Pagar y confirmar viaje", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(80.dp))

        if (showPaymentDialog) {
            AlertDialog(
                onDismissRequest = { showPaymentDialog = false },
                title = { Text("Pago aceptado", fontWeight = FontWeight.Bold) },
                text = { Text("Tu viaje ha sido confirmado exitosamente.") },
                confirmButton = {
                    TextButton(onClick = {
                        showPaymentDialog = false

                        reservedTrip?.let { trip ->
                            val date = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(Date())
                            SharedHistoryState.tripHistoryList.add(
                                TripHistory(
                                    origin = "Santiago de Surco",
                                    destination = trip.destination,
                                    date = date,
                                    status = "Completado"
                                )
                            )
                        }

                        SharedTripState.reservedTrip.value = null
                    }) {
                        Text("Aceptar")
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewProfileScreen() {
    Surface {
        ProfileScreen(onNavigateToHistory = {})
    }
}
