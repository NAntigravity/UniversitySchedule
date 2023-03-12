package com.example.universityschedule.network.api
import com.example.universityschedule.network.models.basicmodels.Room
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RoomApi {
    @GET("buildings/{buildingId}/rooms")
    suspend fun getRoomsInBuilding(@Path("buildingId") buildingId: String): Response<List<Room>>
}