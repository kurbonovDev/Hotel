package com.example.hotel.domain.useCases

import com.example.hotel.data.models.apartaments.RoomInfoItem
import com.example.hotel.data.repository.GetApartmentsRepositoryImp
import com.example.hotel.data.repository.GetRoomInfoImagesImp
import com.example.hotel.domain.model.GeneralState
import com.example.hotel.presentation.utils.parseError
import retrofit2.Retrofit
import javax.inject.Inject

class GetRoomInfoImagesUseCases @Inject constructor(
    private val getRoomInfoImagesImp: GetRoomInfoImagesImp,
    private val retrofit: Retrofit
){
    suspend fun execute(roomId:Int):GeneralState{
        return try {
            val response = getRoomInfoImagesImp.getRoomImages(roomId)
            if (response.isSuccessful){
                GeneralState.Success(response.body())
            }else{
                val errorResponse = parseError(response, retrofit)
                val errorMessage = errorResponse?.message ?: "Unknown error"
                GeneralState.Error(errorMessage)
            }

        }catch (e:Exception){
            GeneralState.Error(e.message.toString())
        }
    }
}