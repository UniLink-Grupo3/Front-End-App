package com.example.testinicial.data.remote.api

import com.example.testinicial.data.remote.model.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/auth/register")
    suspend fun register(@Body request: RegisterRequest)
}
