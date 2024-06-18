package com.example.hotel.presentation.registration.login.viewModel

import androidx.lifecycle.ViewModel
import com.example.hotel.data.models.registration.LoginRequest
import com.example.hotel.domain.model.LoginState
import com.example.hotel.domain.useCases.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Empty)
    val loginUIState: StateFlow<LoginState> get() = _loginState


    suspend fun login(loginRequest: LoginRequest) {
        _loginState.value = LoginState.Loading
        val result = loginUseCase.execute(loginRequest)
        _loginState.value = result
    }

}