package com.example.hotel.data.remote

import com.example.hotel.data.models.apartaments.BookingRequest
import com.example.hotel.data.models.apartaments.BookingResponse
import com.example.hotel.data.models.apartaments.RoomInfoItem
import com.example.hotel.data.models.apartaments.RoomsDTO
import com.example.hotel.data.models.history.HistoryResponse
import com.example.hotel.data.models.user.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface HomeAPI {

    @GET("apartment/get")
    suspend fun getApartments(@Query("page") page: Int): Response<RoomsDTO>


    @GET("apartment/getImages")
    suspend fun getImages(@Query("id") id: Int): Response<RoomInfoItem>

    @POST("booking/add")
    suspend fun bookApartment(@Header("Authorization") token: String, @Body bookingRequest: BookingRequest): Response<BookingResponse>

    @GET("booking/get")
    suspend fun getHistory(@Header("Authorization") token: String):Response<HistoryResponse>

    @GET("user/getCurrent")
    suspend fun getUser(@Header("Authorization") token: String):Response<UserResponse>

}