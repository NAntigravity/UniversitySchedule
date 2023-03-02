package com.example.universityschedule.network.models.dropdownlists

import com.example.universityschedule.network.models.basicmodels.Building

@kotlinx.serialization.Serializable
data class BuildingsList(
    val buildings: List<Building>
)
