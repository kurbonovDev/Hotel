package com.example.hotel.data.models.registration

data class LoginRequest(
    val email: String,
    val password: String
)



data class LoginResponse(
    val status: String,
    val code:String,
    val message: String,
)