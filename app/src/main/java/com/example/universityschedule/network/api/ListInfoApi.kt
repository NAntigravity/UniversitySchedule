package com.example.universityschedule.network.api
import com.example.universityschedule.network.models.dropdownlists.BuildingsList
import com.example.universityschedule.network.models.dropdownlists.GroupsList
import com.example.universityschedule.network.models.dropdownlists.TeachersList
import retrofit2.Response
import retrofit2.http.GET

interface ListInfoApi {

    @GET("groups")
    suspend fun getGroups(): Response<GroupsList>

    @GET("teachers")
    suspend fun getTeachers(): Response<TeachersList>

    @GET("buildings")
    suspend fun getBuildings(): Response<BuildingsList>

}