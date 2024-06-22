package com.example.hotel.domain.useCases

import androidx.paging.PagingData
import com.example.hotel.data.models.apartaments.RoomItem
import com.example.hotel.domain.model.ApartmentsState
import com.example.hotel.domain.repository.GetApartmentsRepository
import com.example.hotel.presentation.utils.parseError
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import javax.inject.Inject

class GetApartmentsUseCase @Inject constructor(
    private val getApartmentsRepository: GetApartmentsRepository,
) {
    suspend fun execute(): Flow<PagingData<RoomItem>> {
        return getApartmentsRepository.getApartments()
    }
}