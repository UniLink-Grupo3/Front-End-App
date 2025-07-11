package com.example.testinicial.data.remote.api

import com.example.testinicial.data.remote.model.LoginRequest
import com.example.testinicial.data.remote.model.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response

interface AuthApi {
    @POST("/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<Map<String, Boolean>>

    @POST("/auth/register")
    suspend fun register(@Body request: RegisterRequest)
}
