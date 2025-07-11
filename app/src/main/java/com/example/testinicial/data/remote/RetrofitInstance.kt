package com.example.testinicial.data.remote

import com.example.testinicial.data.remote.api.AuthApi
import com.example.testinicial.data.remote.StudentApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://movbackfinallll-production.up.railway.app")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val authApi: AuthApi by lazy {
        retrofit.create(AuthApi::class.java)
    }

    val studentApi: StudentApi by lazy {
        retrofit.create(StudentApi::class.java)
    }
}
