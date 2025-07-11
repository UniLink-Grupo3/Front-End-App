package com.example.testinicial.domain.model

data class UniversityStudentWithoutCar(
    val id: Long = 0,
    val name: String,
    val code: String,
    val university: String,
    val image: String = "default_image.png",
    val numberPeople: Int,
    val destination: String,
    val pickup: String,
    val price: Int,
    val latitude: Double,
    val longitude: Double
)
