package com.example.hotel.presentation.main.home.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.hotel.data.models.apartaments.RoomItem
import com.example.hotel.data.pagging.ApartmentRepository
import com.example.hotel.domain.useCases.GetApartmentsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getApartmentsUseCase: GetApartmentsUseCase,
    //private val apartmentsRepository: ApartmentRepository
) : ViewModel() {


    init {
        getApartments()

    }
    //  val apartments:Flow<PagingData<RoomItem>> =
    //    apartmentsRepository.getApartments().cachedIn(viewModelScope)

    private val _getApartmentsState: MutableStateFlow<PagingData<RoomItem>> =
        MutableStateFlow(value = PagingData.empty())
    val apartmentsState: StateFlow<PagingData<RoomItem>> get() = _getApartmentsState


    /*fun getApartments(){
        if (_getApartmentsState.value is ApartmentsState.Empty){
            viewModelScope.launch {
                _getApartmentsState.value = ApartmentsState.Loading
                val result = getApartmentsUseCase.execute()
                _getApartmentsState.value = result
            }
        }
    }

    fun clearData(){
        _getApartmentsState.value = ApartmentsState.Empty
    }*/

    fun getApartments() {
        viewModelScope.launch {
            getApartmentsUseCase.execute()
                .cachedIn(viewModelScope).collect {
                _getApartmentsState.value = it
            }
        }
    }

}