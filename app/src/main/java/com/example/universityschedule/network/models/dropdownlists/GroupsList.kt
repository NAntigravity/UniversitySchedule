package com.example.universityschedule.network.models.dropdownlists

import com.example.universityschedule.network.models.basicmodels.Group

@kotlinx.serialization.Serializable
data class GroupsList(
    val groups: List<Group>
)
