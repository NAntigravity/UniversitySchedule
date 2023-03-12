package com.example.universityschedule

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.universityschedule.network.CoroutinesErrorHandler

object TroubleShooting {
    val failToUpdateToken = MutableLiveData(false)

    val coroutinesErrorHandler = object : CoroutinesErrorHandler {
        override fun onError(message: String) {
            Log.d("!", message)
            CoroutineError()
        }
    }

    fun CoroutineError(){
        // Тост или диалог
    }

}