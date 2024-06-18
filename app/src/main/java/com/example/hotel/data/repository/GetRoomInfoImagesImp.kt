package com.example.hotel.data.repository

import com.example.hotel.data.models.apartaments.RoomInfoItem
import com.example.hotel.data.remote.HomeAPI
import com.example.hotel.domain.repository.GetRoomInfoImages
import retrofit2.Response
import javax.inject.Inject

class GetRoomInfoImagesImp @Inject constructor(private val api:HomeAPI):GetRoomInfoImages {
    override suspend fun getRoomImages(roomId: Int): Response<RoomInfoItem> {
        return api.getImages(roomId)
    }
}