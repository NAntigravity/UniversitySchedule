package com.example.universityschedule.network.models.dropdownlists

import com.example.universityschedule.network.models.basicmodels.Room

@kotlinx.serialization.Serializable
data class RoomList(
    val rooms: List<Room>
)
