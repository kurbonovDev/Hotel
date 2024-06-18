package com.example.hotel.data.models.history

import com.google.gson.annotations.SerializedName

data class HistoryResponse(
    val status: String,
    val code: Int,
    val message:String,
    val data: List<HistoryData>
)

data class HistoryData(
    val id: Int,
    @SerializedName("user_id")
    val userId: Int,
    val price:Double,
    @SerializedName("date_from")
    val dateFrom:String,
    @SerializedName("date_to")
    val dateTo:String,
    val apartments: Apartment
)

data class Apartment(
    val id: Int,
    val name: String,
    val description: String,
    val image: String
)