package com.example.hotel.domain.repository

import com.example.hotel.data.models.apartaments.BookingRequest
import com.example.hotel.data.models.apartaments.BookingResponse
import com.example.hotel.data.models.apartaments.RoomsDTO
import com.example.hotel.data.models.registration.LoginRequest
import com.example.hotel.data.models.registration.LoginResponse
import com.example.hotel.data.models.registration.RegistFinish
import com.example.hotel.data.models.registration.RegistRequest
import com.example.hotel.data.models.registration.RegistResponse
import retrofit2.Response

interface BookApartmentsRepository {
    suspend fun bookApartment(token:String,bookingRequest: BookingRequest):Response<BookingResponse>
}