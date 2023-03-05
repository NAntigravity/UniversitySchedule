package com.example.universityschedule.network.api
import com.example.universityschedule.network.models.UserInfo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface UserApi {

    @GET("users")
    suspend fun getUserInfo(): Response<UserInfo>

    @PUT("users/group")
    suspend fun changeGroup(@Body groupId: String): Response<Nothing>
}