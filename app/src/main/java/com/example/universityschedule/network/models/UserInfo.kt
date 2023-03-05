package com.example.universityschedule.network.models

import com.example.universityschedule.network.models.basicmodels.Group

@kotlinx.serialization.Serializable
data class UserInfo(
    val role: Int,
    val login: String,
    val group: Group
)
