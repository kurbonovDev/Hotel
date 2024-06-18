package com.example.hotel.data.remote

import com.example.hotel.data.models.apartaments.RoomsDTO
import com.example.hotel.data.models.registration.LoginRequest
import com.example.hotel.data.models.registration.LoginResponse
import com.example.hotel.data.models.registration.RegistFinish
import com.example.hotel.data.models.registration.RegistRequest
import com.example.hotel.data.models.registration.RegistResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegistApi {

    @POST("auth/preRegister")
    suspend fun preRegist(@Body registRequest: RegistRequest):Response<RegistResponse>

    @POST("auth/regiterUser")
    suspend fun registUser(@Body registFinish: RegistFinish):Response<RegistResponse>

    @POST("auth/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest):Response<LoginResponse>


}