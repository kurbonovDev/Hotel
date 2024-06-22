package com.example.hotel.data.datasource

import com.example.hotel.data.models.apartaments.RoomsDTO
import retrofit2.Response

interface ApartmentDataSource {
    suspend fun getApartments(page:Int): Response<RoomsDTO>
}