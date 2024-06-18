package com.example.hotel.data.repository

import com.example.hotel.data.models.registration.RegistFinish
import com.example.hotel.data.models.registration.RegistRequest
import com.example.hotel.data.models.registration.RegistResponse
import com.example.hotel.data.remote.RegistApi
import com.example.hotel.domain.repository.PreRegistRepository
import com.example.hotel.domain.repository.RegistRepository
import retrofit2.Response
import javax.inject.Inject

class RegistRepositoryImp @Inject constructor(private val api:RegistApi): RegistRepository {
    override suspend fun registerUser(registFinish: RegistFinish): Response<RegistResponse> {
        return api.registUser(registFinish)
    }

}