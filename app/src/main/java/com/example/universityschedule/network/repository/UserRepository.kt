package com.example.universityschedule.network.repository

import com.example.universityschedule.network.Network
import com.example.universityschedule.network.api.UserApi
import com.example.universityschedule.network.apiRequestFlow

class UserRepository {
    private val userApi: UserApi = Network.getUserApi()

    fun getUserInfo() = apiRequestFlow {
        userApi.getUserInfo()
    }

    fun changeGroup(desiredGroup: String) = apiRequestFlow {
        userApi.changeGroup(desiredGroup)
    }
}