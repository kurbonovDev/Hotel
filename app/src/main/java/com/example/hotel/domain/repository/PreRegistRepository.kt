package com.example.hotel.domain.repository

import com.example.hotel.data.models.registration.RegistRequest
import com.example.hotel.data.models.registration.RegistResponse
import retrofit2.Response

interface PreRegistRepository {
    suspend fun preRegister(registRequest: RegistRequest):Response<RegistResponse>
}