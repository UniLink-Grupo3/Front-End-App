package com.example.testinicial.data.repository

import com.example.testinicial.data.remote.api.AuthApi
import com.example.testinicial.data.remote.api.LoginRequest
import com.example.testinicial.data.remote.model.RegisterRequest

class AuthRepository(private val api: AuthApi) {
    suspend fun login(email: String, password: String) =
        api.login(LoginRequest(email, password))

    suspend fun register(username: String, email: String, phone: String, password: String) =
        api.register(RegisterRequest(username, email, phone, password))

    suspend fun getMe() = api.getMe()
}
