package com.example.hotel.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.hotel.data.datasource.ApartmentDataSource
import com.example.hotel.data.models.apartaments.RoomItem
import com.example.hotel.data.models.apartaments.RoomsDTO
import com.example.hotel.data.models.registration.LoginRequest
import com.example.hotel.data.models.registration.LoginResponse
import com.example.hotel.data.models.registration.RegistFinish
import com.example.hotel.data.models.registration.RegistRequest
import com.example.hotel.data.models.registration.RegistResponse
import com.example.hotel.data.pagging.ApartmentPagingSource
import com.example.hotel.data.pagging.ApartmentRepository
import com.example.hotel.data.remote.HomeAPI
import com.example.hotel.data.remote.RegistApi
import com.example.hotel.domain.repository.GetApartmentsRepository
import com.example.hotel.domain.repository.LoginRepository
import com.example.hotel.domain.repository.PreRegistRepository
import com.example.hotel.domain.repository.RegistRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class GetApartmentsRepositoryImp @Inject constructor(
    private val apartmentDataSource: ApartmentDataSource
): GetApartmentsRepository {
    override suspend fun getApartments(): Flow<PagingData<RoomItem>> {
        return Pager(
            config = PagingConfig(pageSize = 10, maxSize = 200, initialLoadSize = 10, prefetchDistance = 5),
            pagingSourceFactory = { ApartmentPagingSource(apartmentDataSource) }
        ).flow
    }
}