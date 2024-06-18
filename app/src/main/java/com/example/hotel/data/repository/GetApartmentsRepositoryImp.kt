package com.example.hotel.data.repository

import com.example.hotel.data.models.apartaments.RoomsDTO
import com.example.hotel.data.models.registration.LoginRequest
import com.example.hotel.data.models.registration.LoginResponse
import com.example.hotel.data.models.registration.RegistFinish
import com.example.hotel.data.models.registration.RegistRequest
import com.example.hotel.data.models.registration.RegistResponse
import com.example.hotel.data.remote.HomeAPI
import com.example.hotel.data.remote.RegistApi
import com.example.hotel.domain.repository.GetApartmentsRepository
import com.example.hotel.domain.repository.LoginRepository
import com.example.hotel.domain.repository.PreRegistRepository
import com.example.hotel.domain.repository.RegistRepository
import retrofit2.Response
import javax.inject.Inject

class GetApartmentsRepositoryImp @Inject constructor(private val api:HomeAPI): GetApartmentsRepository {
    override suspend fun getApartments(): Response<RoomsDTO> {
        return api.getApartments()
    }
}