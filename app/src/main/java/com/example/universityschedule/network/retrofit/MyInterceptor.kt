package com.example.universityschedule.network.retrofit

import com.example.universityschedule.MainApplication
import com.example.universityschedule.network.Network
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class MyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request().newBuilder().apply {
            addHeader("accept", "application/json")
            //addHeader("content-Type", "application/x-www-form-urlencoded")
            addHeader("Authorization", "Bearer ${Network.getSharedPrefs(MainApplication.AccessToken)}")
        }.build()

        return chain.proceed(request)
    }
}