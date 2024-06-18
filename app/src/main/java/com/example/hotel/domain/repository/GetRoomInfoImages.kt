package com.example.hotel.domain.repository

import com.example.hotel.data.models.apartaments.RoomInfoItem
import retrofit2.Response

interface GetRoomInfoImages {
    suspend fun getRoomImages(roomId: Int):Response<RoomInfoItem>
}