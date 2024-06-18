package com.example.hotel.presentation.main.home.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotel.domain.model.ApartmentsState
import com.example.hotel.domain.model.LoginState
import com.example.hotel.domain.useCases.GetApartmentsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getApartmentsUseCase: GetApartmentsUseCase
):ViewModel() {
    private val _getApartmentsState = MutableStateFlow<ApartmentsState>(ApartmentsState.Empty)
    val apartmentsState: StateFlow<ApartmentsState> get() = _getApartmentsState


    fun getApartments(){
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
    }

}