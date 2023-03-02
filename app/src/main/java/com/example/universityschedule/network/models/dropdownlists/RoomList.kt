package com.example.universityschedule.network.models.dropdownlists

import com.example.appfortests.retrofit.models.basicmodels.Room

@kotlinx.serialization.Serializable
data class RoomList(
    val rooms: List<Room>
)
