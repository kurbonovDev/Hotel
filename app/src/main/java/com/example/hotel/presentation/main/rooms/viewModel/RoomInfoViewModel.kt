package com.example.hotel.presentation.main.rooms.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotel.data.models.apartaments.BookingRequest
import com.example.hotel.data.models.apartaments.RoomInfoItem
import com.example.hotel.domain.model.ApartmentsState
import com.example.hotel.domain.model.GeneralState
import com.example.hotel.domain.useCases.BookApartmentUseCase
import com.example.hotel.domain.useCases.GetRoomInfoImagesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomInfoViewModel @Inject constructor(
    private val getRoomInfoImagesUseCases: GetRoomInfoImagesUseCases,
    private val bookApartmentUseCases: BookApartmentUseCase
) : ViewModel() {

    private val _getRoomInfoState = MutableStateFlow<GeneralState>(GeneralState.Empty)
    val roomInfoState: StateFlow<GeneralState> get() = _getRoomInfoState

    private val _bookApartmentState = MutableStateFlow<GeneralState>(GeneralState.Empty)
    val bookApartmentState: StateFlow<GeneralState> get() = _bookApartmentState


    fun getRoomImages(roomId: Int) {
        viewModelScope.launch {
            _getRoomInfoState.value = GeneralState.Loading
            val result = getRoomInfoImagesUseCases.execute(roomId)
            _getRoomInfoState.value = result
        }
    }


    fun bookApartment(token: String, bookingRequest: BookingRequest,callback: () -> Unit) {
        viewModelScope.launch {
            _bookApartmentState.value = GeneralState.Loading
            val result = bookApartmentUseCases.execute(token,bookingRequest)
            _bookApartmentState.value = result
            callback()
        }
    }



}