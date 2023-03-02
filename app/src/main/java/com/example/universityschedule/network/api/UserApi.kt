package com.example.universityschedule.network.api
import com.example.universityschedule.network.models.UserInfo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApi {

    @GET("user")
    suspend fun getUserInfo(): Response<UserInfo>

    @POST("user")
    suspend fun changeGroup(@Body groupId: String): Response<UserInfo>  //TODO( возможно этого запроса не будет )
}