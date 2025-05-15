package com.example.testinicial.data

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.painter.Painter

data class ReservedTrip(
    val vehicleName: String,
    val vehicleYear: String,
    val vehicleModel: String,
    val destination: String,
    val vehicleImage: Painter,
)

object SharedTripState {
    var reservedTrip = mutableStateOf<ReservedTrip?>(null)
}
