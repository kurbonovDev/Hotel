package com.example.hotel.data.repository

import com.example.hotel.data.models.registration.RegistRequest
import com.example.hotel.data.models.registration.RegistResponse
import com.example.hotel.data.remote.RegistApi
import com.example.hotel.domain.repository.PreRegistRepository
import retrofit2.Response
import javax.inject.Inject

class PreRegistRepositoryImp @Inject constructor(private val api:RegistApi):PreRegistRepository {
    override suspend fun preRegister(registRequest: RegistRequest): Response<RegistResponse >{
        return api.preRegist(registRequest)
    }
}