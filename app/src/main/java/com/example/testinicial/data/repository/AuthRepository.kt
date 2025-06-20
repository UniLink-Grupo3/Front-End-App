// AuthRepository.kt
package com.example.testinicial.data.repository

import com.example.testinicial.data.remote.api.AuthApi
import com.example.testinicial.data.remote.api.LoginRequest
import com.example.testinicial.data.remote.model.RegisterRequest
import com.example.testinicial.data.remote.model.UserResponse

class AuthRepository(private val api: AuthApi) {
    suspend fun login(email: String, password: String): UserResponse {
        return api.login(LoginRequest(email, password))
    }

    suspend fun register(registerRequest: RegisterRequest): UserResponse {
        return api.register(registerRequest)
    }

    suspend fun getMe(): UserResponse {
        return api.getMe()
    }
}
