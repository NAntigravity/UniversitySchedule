package com.example.universityschedule.network.testfiles

import com.example.universityschedule.network.Network
import com.example.universityschedule.network.apiRequestFlow

class TestRepository {
    private val testApi: UserTestApi = Network.getUserTestApi()

    fun getUserData() = apiRequestFlow {
        testApi.getUserInfo()
    }
}