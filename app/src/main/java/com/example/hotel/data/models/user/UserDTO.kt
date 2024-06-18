package com.example.hotel.data.models.user


data class UserResponse(
    val status: String,
    val code: Int,
    val message: String,
    val data: User
)

data class User(
    val name: String,
    val phone: String,
    val email: String
)