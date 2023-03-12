package com.example.universityschedule

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.universityschedule.network.CoroutinesErrorHandler

object TroubleShooting {
    val failToUpdateToken = MutableLiveData(false)

    val coroutinesErrorHandler = object : CoroutinesErrorHandler {
        override fun onError(message: String) {
            Log.d("!", message)
            coroutineError()
        }
    }

    fun coroutineError(){
        Toast.makeText(MainApplication.applicationContext(), "Coroutine Error!", Toast.LENGTH_LONG).show()
    }

}