package com.example.hotel.domain.repository

import androidx.paging.PagingData
import com.example.hotel.data.models.apartaments.RoomItem
import com.example.hotel.data.models.apartaments.RoomsDTO
import com.example.hotel.data.models.registration.LoginRequest
import com.example.hotel.data.models.registration.LoginResponse
import com.example.hotel.data.models.registration.RegistFinish
import com.example.hotel.data.models.registration.RegistRequest
import com.example.hotel.data.models.registration.RegistResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface GetApartmentsRepository {
    suspend fun getApartments(): Flow<PagingData<RoomItem>>
}

