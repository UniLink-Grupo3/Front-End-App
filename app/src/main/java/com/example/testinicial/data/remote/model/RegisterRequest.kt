package com.example.testinicial.data.remote.model

data class RegisterRequest(
    val username: String,
    val email: String,
    val phone: String,
    val password: String
)