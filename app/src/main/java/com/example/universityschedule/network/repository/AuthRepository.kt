package com.example.universityschedule.network.repository

import com.example.universityschedule.network.Network
import com.example.universityschedule.network.api.AuthApi
import com.example.universityschedule.network.apiRequestFlow
import com.example.universityschedule.network.models.basicmodels.LoginRequestBody

class AuthRepository {
    private val authApi: AuthApi = Network.getAuthApi()

    fun login(loginData: LoginRequestBody) = apiRequestFlow {
        authApi.login(loginData)
    }

    fun logout() = apiRequestFlow {
        authApi.logout()
    }

    fun register(registerData: LoginRequestBody) = apiRequestFlow {
        authApi.register(registerData)
    }
}