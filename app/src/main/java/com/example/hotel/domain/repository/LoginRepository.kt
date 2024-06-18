package com.example.hotel.domain.repository

import com.example.hotel.data.models.registration.LoginRequest
import com.example.hotel.data.models.registration.LoginResponse
import com.example.hotel.data.models.registration.RegistFinish
import com.example.hotel.data.models.registration.RegistRequest
import com.example.hotel.data.models.registration.RegistResponse
import retrofit2.Response

interface LoginRepository {
    suspend fun login(loginRequest: LoginRequest):Response<LoginResponse>
}