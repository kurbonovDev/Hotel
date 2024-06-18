package com.example.hotel.data.models.registration

data class RegistResponse(
    val status:String,
    val code:String,
    val message:String
)


data class RegistRequest(
    val name: String,
    val email: String,
    val password: String,
    val phone: String
)

data class RegistFinish(
    val email: String,
    val code:String
)