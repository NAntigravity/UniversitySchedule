package com.example.universityschedule.network

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow

interface CoroutinesErrorHandler {
    fun onError(message: String)
}

abstract class BaseViewModel : ViewModel() {
    private var mJob: Job? = null

    fun <T> baseRequest(
        liveData: MutableLiveData<T>,
        errorHandler: CoroutinesErrorHandler,
        request: Flow<T>
    ) {
        mJob = viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, error ->
            viewModelScope.launch(Dispatchers.Main) {
                errorHandler.onError(error.localizedMessage ?: "Error occured! Please try again.")
            }
        }) {
            request.collect {
                withContext(Dispatchers.Main) {
                    liveData.postValue(it)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        mJob?.let {
            if (it.isActive) {
                it.cancel()
            }
        }
    }
}