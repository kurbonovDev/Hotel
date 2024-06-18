package com.example.hotel.domain.useCases

import android.util.Log
import com.example.hotel.data.models.registration.LoginRequest
import com.example.hotel.data.models.registration.RegistFinish
import com.example.hotel.data.models.registration.RegistRequest
import com.example.hotel.data.models.registration.RegistResponse
import com.example.hotel.data.repository.LoginRepositoryImp
import com.example.hotel.data.repository.PreRegistRepositoryImp
import com.example.hotel.data.repository.RegistRepositoryImp
import com.example.hotel.domain.model.LoginState
import com.example.hotel.domain.model.RegisterState
import com.example.hotel.presentation.utils.parseError
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepositoryImp: LoginRepositoryImp,
    private val retrofit: Retrofit
) {
    suspend fun execute(loginRequest: LoginRequest): LoginState {
        return try {
            val response = loginRepositoryImp.login(loginRequest)
            if (response.isSuccessful){
                LoginState.Success(response.body())
            }else{
                val errorResponse = parseError(response, retrofit)
                val errorMessage = errorResponse?.message ?: "Unknown error"
                LoginState.Error(errorMessage)
            }
        } catch (e: Exception) {
            LoginState.Error(errorMessage = e.message.toString())
        }
    }
}