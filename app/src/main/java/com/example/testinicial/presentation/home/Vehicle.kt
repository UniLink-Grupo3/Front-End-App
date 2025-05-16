package com.example.testinicial.presentation.home

import androidx.compose.ui.graphics.painter.Painter

data class Vehicle(
    val name: String,
    val model: String,
    val year: String,
    val destination: String,
    val image: Painter
)
