package com.example.hotel.domain.model

import com.example.hotel.data.models.apartaments.RoomsDTO
import com.example.hotel.data.models.registration.LoginResponse
import com.example.hotel.data.models.registration.RegistResponse


sealed class RegisterState() {
    data object Loading : RegisterState()
    data class Success(val userResponse: RegistResponse?) : RegisterState()
    data class Error(val errorMessage: String) : RegisterState()
    data object Empty : RegisterState()
}


sealed class LoginState() {
    data object Loading : LoginState()
    data class Success(val loginResponse: LoginResponse?) : LoginState()
    data class Error(val errorMessage: String) : LoginState()
    data object Empty : LoginState()
}


sealed class ApartmentsState() {
    data object Loading : ApartmentsState()
    data class Success(val apartments: RoomsDTO?) : ApartmentsState()
    data class Error(val errorMessage: String) : ApartmentsState()
    data object Empty : ApartmentsState()
}


sealed class GeneralState() {
    data object Loading : GeneralState()
    data class Success<T>(val data: T) : GeneralState()
    data class Error(val errorMessage: String) : GeneralState()
    data object Empty : GeneralState()
}