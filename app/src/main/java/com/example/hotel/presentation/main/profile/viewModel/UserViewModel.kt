package com.example.hotel.presentation.main.profile.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotel.domain.model.GeneralState
import com.example.hotel.domain.model.LoginState
import com.example.hotel.domain.useCases.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase
):ViewModel() {

    private val _userState = MutableStateFlow<GeneralState>(GeneralState.Empty)
    val userUIState: StateFlow<GeneralState> get() = _userState

     fun getUser(token:String){
         viewModelScope.launch {
             _userState.value = GeneralState.Loading
             val result = getUserUseCase.execute(token = token )
             _userState.value = result
         }

    }
}