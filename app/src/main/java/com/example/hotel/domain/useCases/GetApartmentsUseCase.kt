package com.example.hotel.domain.useCases

import android.util.Log
import com.example.hotel.data.models.registration.LoginRequest
import com.example.hotel.data.models.registration.RegistFinish
import com.example.hotel.data.models.registration.RegistRequest
import com.example.hotel.data.models.registration.RegistResponse
import com.example.hotel.data.repository.GetApartmentsRepositoryImp
import com.example.hotel.data.repository.LoginRepositoryImp
import com.example.hotel.data.repository.PreRegistRepositoryImp
import com.example.hotel.data.repository.RegistRepositoryImp
import com.example.hotel.domain.model.ApartmentsState
import com.example.hotel.domain.model.LoginState
import com.example.hotel.domain.model.RegisterState
import com.example.hotel.presentation.utils.parseError
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Inject

class GetApartmentsUseCase @Inject constructor(
    private val getApartmentsRepositoryImp: GetApartmentsRepositoryImp,
    private val retrofit: Retrofit
) {
    suspend fun execute(): ApartmentsState {
        return try {
            val response = getApartmentsRepositoryImp.getApartments()
            if (response.isSuccessful){
                if (response.body()?.data!!.isEmpty()){
                    ApartmentsState.Empty
                }else{
                    ApartmentsState.Success(response.body())
                }
            }else{
                val errorResponse = parseError(response, retrofit)
                val errorMessage = errorResponse?.message ?: "Unknown error"
                ApartmentsState.Error(errorMessage)
            }
        } catch (e: Exception) {
            ApartmentsState.Error(errorMessage = e.message.toString())
        }
    }
}