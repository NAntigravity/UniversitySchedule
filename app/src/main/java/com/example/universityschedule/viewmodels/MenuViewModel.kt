package com.example.universityschedule.viewmodels

import androidx.lifecycle.MutableLiveData
import com.example.universityschedule.TroubleShooting
import com.example.universityschedule.network.ApiResponse
import com.example.universityschedule.network.BaseViewModel
import com.example.universityschedule.network.models.UserInfo
import com.example.universityschedule.network.repository.UserRepository

class MenuViewModel: BaseViewModel() {
    private val userRepository = UserRepository()
    private val _data = MutableLiveData<ApiResponse<UserInfo>>()

    val userData = _data

    init{
        baseRequest(_data, TroubleShooting.coroutinesErrorHandler, userRepository.getUserInfo())
    }

}