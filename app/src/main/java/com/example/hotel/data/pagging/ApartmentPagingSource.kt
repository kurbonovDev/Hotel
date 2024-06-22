package com.example.hotel.data.pagging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.hotel.data.datasource.ApartmentDataSource
import com.example.hotel.data.models.apartaments.PreData
import com.example.hotel.data.models.apartaments.RoomItem
import com.example.hotel.data.models.apartaments.RoomsDTO
import com.example.hotel.domain.repository.GetApartmentsRepository
import javax.inject.Inject

class ApartmentPagingSource @Inject constructor(
    private val apartmentDataSource: ApartmentDataSource
) : PagingSource<Int, RoomItem>() {
    override fun getRefreshKey(state: PagingState<Int, RoomItem>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RoomItem> {
        return try {
            val position = params.key ?: 1
            val response = apartmentDataSource.getApartments(position)

            if (response.isSuccessful) {
                LoadResult.Page(
                    data = response.body()!!.data.records,
                    prevKey = if (position == 1) null else (position - 1),
                    nextKey = if (position != response.body()!!.data.totalPages) position + 1 else null
                )
            } else {
                LoadResult.Error(throw Exception("no Response"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}