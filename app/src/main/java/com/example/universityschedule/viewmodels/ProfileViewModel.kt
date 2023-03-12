package com.example.universityschedule.viewmodels

import androidx.lifecycle.MutableLiveData
import com.example.universityschedule.TroubleShooting
import com.example.universityschedule.network.ApiResponse
import com.example.universityschedule.network.BaseViewModel
import com.example.universityschedule.network.repository.AuthRepository
import com.example.universityschedule.network.repository.UserRepository

class ProfileViewModel: BaseViewModel() {
    private val authRepository = AuthRepository()
    private val userRepository = UserRepository()

    val data = MutableLiveData<ApiResponse<Nothing>>()

    val changeGroupData = MutableLiveData<ApiResponse<Nothing>>()


    fun logout(){
        baseRequest(data, TroubleShooting.coroutinesErrorHandler, authRepository.logout())
    }

    fun changeGroup(newGroup: String){
        baseRequest(changeGroupData, TroubleShooting.coroutinesErrorHandler, userRepository.changeGroup(newGroup))
    }
}