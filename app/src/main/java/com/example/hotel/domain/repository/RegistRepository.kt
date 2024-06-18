package com.example.hotel.domain.repository

import com.example.hotel.data.models.registration.RegistFinish
import com.example.hotel.data.models.registration.RegistRequest
import com.example.hotel.data.models.registration.RegistResponse
import retrofit2.Response

interface RegistRepository {
    suspend fun registerUser(registFinish: RegistFinish):Response<RegistResponse>
}