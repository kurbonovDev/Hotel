package com.example.hotel.presentation.main.history.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotel.domain.model.GeneralState
import com.example.hotel.domain.useCases.GetApartmentsHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getApartmentsHistoryUseCase: GetApartmentsHistoryUseCase
):ViewModel() {

    private val _getApartmentsHistoryState = MutableStateFlow<GeneralState>(GeneralState.Empty)
    val getApartmentsHistoryState: StateFlow<GeneralState> get() = _getApartmentsHistoryState

    fun getApartmentHistory(token:String){
        viewModelScope.launch {
            _getApartmentsHistoryState.value = GeneralState.Loading
            val result = getApartmentsHistoryUseCase.execute(token)
            _getApartmentsHistoryState.value = result
        }
    }
}