package com.example.universityschedule.network.testfiles

import retrofit2.Response
import retrofit2.http.GET

interface UserTestApi {
    @GET("user/info")
    suspend fun getUserInfo(): Response<UserInfo>
}