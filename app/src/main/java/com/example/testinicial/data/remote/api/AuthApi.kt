package com.example.testinicial.data.remote.api

import com.example.testinicial.data.remote.model.RegisterRequest
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.net.CookieManager
import java.net.CookiePolicy
import com.example.testinicial.data.remote.model.UserResponse

data class LoginRequest(val email: String, val password: String)

interface AuthApi {
    @POST("/auth/login")
    suspend fun login(@Body request: LoginRequest): UserResponse

    @POST("/auth/register")
    suspend fun register(@Body request: RegisterRequest): UserResponse

    @GET("/auth/me")
    suspend fun getMe(): UserResponse
}

object AuthService {
    private val cookieManager = CookieManager().apply {
        setCookiePolicy(CookiePolicy.ACCEPT_ALL)
    }

    private val client = OkHttpClient.Builder()
        .cookieJar(JavaNetCookieJar(cookieManager))
        .build()

    val api: AuthApi = Retrofit.Builder()
        .baseUrl("https://moviles-back-production.up.railway.app")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(AuthApi::class.java)
}
