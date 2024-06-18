package com.example.hotel.data.repository

import com.example.hotel.data.models.registration.LoginRequest
import com.example.hotel.data.models.registration.LoginResponse
import com.example.hotel.data.models.registration.RegistFinish
import com.example.hotel.data.models.registration.RegistRequest
import com.example.hotel.data.models.registration.RegistResponse
import com.example.hotel.data.remote.RegistApi
import com.example.hotel.domain.repository.LoginRepository
import com.example.hotel.domain.repository.PreRegistRepository
import com.example.hotel.domain.repository.RegistRepository
import retrofit2.Response
import javax.inject.Inject

class LoginRepositoryImp @Inject constructor(private val api:RegistApi): LoginRepository {
    override suspend fun login(loginRequest: LoginRequest): Response<LoginResponse> {
        return api.loginUser(loginRequest)
    }

}