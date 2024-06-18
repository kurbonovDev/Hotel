package com.example.hotel.domain.repository

import com.example.hotel.data.models.apartaments.RoomsDTO
import com.example.hotel.data.models.registration.LoginRequest
import com.example.hotel.data.models.registration.LoginResponse
import com.example.hotel.data.models.registration.RegistFinish
import com.example.hotel.data.models.registration.RegistRequest
import com.example.hotel.data.models.registration.RegistResponse
import com.example.hotel.data.models.user.UserResponse
import retrofit2.Response

interface GetUserRepository {
    suspend fun getUser(token:String):Response<UserResponse>
}