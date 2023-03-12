package com.example.universityschedule.network.api

import com.example.universityschedule.network.models.LoginResponse
import com.example.universityschedule.network.models.basicmodels.LoginRequestBody
import com.example.universityschedule.network.models.basicmodels.RegisterRequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/login")
    suspend fun login(@Body authForm: LoginRequestBody): Response<LoginResponse>

    @GET("auth/refresh")
    suspend fun refreshToken(@Header("Authorization") token: String): Response<LoginResponse>

    @POST("auth/logout")
    suspend fun logout(): Response<Nothing>
    @POST("auth/register")
    suspend fun register(@Body registerForm: RegisterRequestBody): Response<LoginResponse>
}