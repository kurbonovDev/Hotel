package com.example.hotel.data.datasource

import com.example.hotel.data.models.apartaments.RoomsDTO
import com.example.hotel.data.remote.HomeAPI
import retrofit2.Response
import javax.inject.Inject

class ApartmentDataSourceImpl @Inject constructor(
    private val api:HomeAPI
):ApartmentDataSource {
    override suspend fun getApartments(page: Int): Response<RoomsDTO> {
        return api.getApartments(page)
    }
}