package com.example.universityschedule.viewmodels

import androidx.lifecycle.MutableLiveData
import com.example.universityschedule.TroubleShooting
import com.example.universityschedule.network.ApiResponse
import com.example.universityschedule.network.BaseViewModel
import com.example.universityschedule.network.models.UserInfo
import com.example.universityschedule.network.repository.UserRepository

class ApplicationLoadingViewModel: BaseViewModel() {

    private val profileRepository: UserRepository = UserRepository()

    fun checkUserAlive(liveData: MutableLiveData<ApiResponse<UserInfo>>){
        baseRequest(liveData, TroubleShooting.coroutinesErrorHandler, profileRepository.getUserInfo())
    }
}