package com.example.universityschedule.network.repository


import com.example.universityschedule.network.Network
import com.example.universityschedule.network.api.ListInfoApi
import com.example.universityschedule.network.api.RoomApi
import com.example.universityschedule.network.apiRequestFlow

class InformationRepository {
    private val roomApi: RoomApi = Network.getRoomApi()
    private val infoApi: ListInfoApi = Network.getListInfoApi()

    fun getRoomsInBuilding(buildingId: Int) = apiRequestFlow {
        roomApi.getRoomsInBuilding(buildingId)
    }

    fun getGroups() = apiRequestFlow {
        infoApi.getGroups()
    }

    fun getTeachers() = apiRequestFlow {
        infoApi.getTeachers()
    }

    fun getBuildings() = apiRequestFlow {
        infoApi.getBuildings()
    }
}