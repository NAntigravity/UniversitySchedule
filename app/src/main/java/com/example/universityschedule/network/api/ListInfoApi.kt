package com.example.universityschedule.network.api

import com.example.universityschedule.network.models.basicmodels.Building
import com.example.universityschedule.network.models.basicmodels.Group
import com.example.universityschedule.network.models.basicmodels.Subject
import com.example.universityschedule.network.models.basicmodels.Teacher
import retrofit2.Response
import retrofit2.http.GET

interface ListInfoApi {

    @GET("groups")
    suspend fun getGroups(): Response<List<Group>>

    @GET("teachers")
    suspend fun getTeachers(): Response<List<Teacher>>

    @GET("buildings")
    suspend fun getBuildings(): Response<List<Building>>

    @GET("subjects")
    suspend fun getSubjects(): Response<List<Subject>>

}