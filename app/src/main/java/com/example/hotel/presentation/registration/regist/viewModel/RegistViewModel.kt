package com.example.hotel.presentation.registration.regist.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotel.data.models.registration.RegistFinish
import com.example.hotel.data.models.registration.RegistRequest
import com.example.hotel.domain.model.RegisterState
import com.example.hotel.domain.useCases.PreRegistUseCase
import com.example.hotel.domain.useCases.RegistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.security.PrivateKey
import javax.inject.Inject

@HiltViewModel
class RegistViewModel @Inject constructor(
    private val preRegistUseCase: PreRegistUseCase,
    private val registUseCase: RegistUseCase,
) : ViewModel() {

    private val _preregister = MutableStateFlow<RegisterState>(RegisterState.Empty)
    val preRegisterState: StateFlow<RegisterState> get() = _preregister

    private val _register = MutableStateFlow<RegisterState>(RegisterState.Empty)
    val registerState: StateFlow<RegisterState> get() = _register

    fun preRegistUser(registRequest: RegistRequest) {
        viewModelScope.launch {
            _preregister.value = RegisterState.Loading
            val result = preRegistUseCase.execute(registRequest)
            _preregister.value = result
        }
    }


    fun registUser(registFinish: RegistFinish) {
        viewModelScope.launch {
            viewModelScope.launch {
                _register.value = RegisterState.Loading
                val result = registUseCase.execute(registFinish)
                _register.value = result
            }
        }
    }
}