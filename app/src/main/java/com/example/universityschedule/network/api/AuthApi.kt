package com.example.universityschedule.network.api
import com.example.universityschedule.network.Message
import com.example.universityschedule.network.models.basicmodels.LoginRequestBody
import com.example.universityschedule.network.models.LoginResponse
import retrofit2.Response

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/login")
    suspend fun login(@Body auth: LoginRequestBody): Response<LoginResponse>

    @GET("auth/refresh")
    suspend fun refreshToken(@Header("Authorization") token: String): Response<LoginResponse>

    @POST("auth/logout")
    suspend fun logout(): Response<Message>
}