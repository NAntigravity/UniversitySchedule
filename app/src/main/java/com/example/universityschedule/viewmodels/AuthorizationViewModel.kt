package com.example.universityschedule.viewmodels

import androidx.lifecycle.MutableLiveData
import com.example.universityschedule.TroubleShooting
import com.example.universityschedule.network.ApiResponse
import com.example.universityschedule.network.BaseViewModel
import com.example.universityschedule.network.models.LoginResponse
import com.example.universityschedule.network.models.basicmodels.LoginRequestBody
import com.example.universityschedule.network.models.basicmodels.RegisterRequestBody
import com.example.universityschedule.network.repository.AuthRepository

class AuthorizationViewModel : BaseViewModel() {

    private val authRepository = AuthRepository()
    private val _requestStatus: MutableLiveData<ApiResponse<LoginResponse>> =
        MutableLiveData<ApiResponse<LoginResponse>>()

    val status = _requestStatus

    fun login(email: String, password: String) {
        baseRequest(
            _requestStatus,
            TroubleShooting.coroutinesErrorHandler,
            authRepository.login(LoginRequestBody(email = email, password = password))
        )
    }

    fun register(email: String, password: String, name: String) {
        baseRequest(
            _requestStatus,
            TroubleShooting.coroutinesErrorHandler,
            authRepository.register(RegisterRequestBody(name = name , email = email, password = password))
        )
    }
}
