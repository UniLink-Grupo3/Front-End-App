package com.example.testinicial.di

import com.example.testinicial.data.remote.api.AuthApi
import com.example.testinicial.data.remote.session.SessionCookieJar
import com.example.testinicial.data.repository.AuthRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppModule {
    private val cookieJar = SessionCookieJar()

    private val client = OkHttpClient.Builder()
        .cookieJar(cookieJar)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://moviles-back-production.up.railway.app")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val authApi: AuthApi = retrofit.create(AuthApi::class.java)
    val authRepository = AuthRepository(authApi)
}
