package com.example.testinicial.data.remote

import com.example.testinicial.presentation.home.UniversityStudentWithoutCar
import retrofit2.http.Body
import retrofit2.http.POST

interface StudentApi {
    @POST("students")
    suspend fun postStudent(
        @Body student: UniversityStudentWithoutCar
    )
}
