package com.example.universityschedule.network.api
import com.example.universityschedule.network.models.dropdownlists.RoomList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RoomApi {

    @GET("buildings/{buildingId}/rooms")
    suspend fun getRoomsInBuilding(@Path("buildingId") buildingId: Int): Response<RoomList>
}