package com.example.universityschedule.viewmodels

import androidx.lifecycle.MutableLiveData
import com.example.universityschedule.TroubleShooting
import com.example.universityschedule.network.ApiResponse
import com.example.universityschedule.network.BaseViewModel
import com.example.universityschedule.network.repository.AuthRepository

class ProfileViewModel: BaseViewModel() {
    private val authRepository = AuthRepository()

    val data = MutableLiveData<ApiResponse<Nothing>>()


    fun logout(){
        baseRequest(data, TroubleShooting.coroutinesErrorHandler, authRepository.logout())
    }

    fun changeGroup(){

    }
}