package com.example.hotel.domain.useCases

import android.util.Log
import com.example.hotel.data.models.registration.RegistRequest
import com.example.hotel.data.models.registration.RegistResponse
import com.example.hotel.data.repository.PreRegistRepositoryImp
import com.example.hotel.domain.model.RegisterState
import com.example.hotel.presentation.utils.parseError
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Inject

class PreRegistUseCase @Inject constructor(
    private val preRegistRepositoryImp: PreRegistRepositoryImp,
    private val retrofit: Retrofit
) {
    suspend fun execute(registRequest: RegistRequest): RegisterState {
        return try {
            val response = preRegistRepositoryImp.preRegister(registRequest)
            if (response.isSuccessful){
                RegisterState.Success(response.body())
            }else{
                val errorResponse = parseError(response, retrofit)
                val errorMessage = errorResponse?.message ?: "Unknown error"
                RegisterState.Error(errorMessage)
            }
        } catch (e: Exception) {
            RegisterState.Error(errorMessage = e.message.toString())
        }
    }
}