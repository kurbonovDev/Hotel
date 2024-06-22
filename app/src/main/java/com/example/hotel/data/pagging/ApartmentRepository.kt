package com.example.hotel.data.pagging

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.hotel.data.datasource.ApartmentDataSource
import com.example.hotel.data.models.apartaments.RoomItem
import com.example.hotel.domain.repository.GetApartmentsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ApartmentRepository @Inject constructor(
    private val apartmentDataSource: ApartmentDataSource
)
{
    fun getApartments(): Flow<PagingData<RoomItem>> = Pager(
        config = PagingConfig(pageSize = 10, maxSize = 200, initialLoadSize = 10, prefetchDistance = 5),
        pagingSourceFactory = { ApartmentPagingSource(apartmentDataSource) }
    ).flow
}