package com.example.universityschedule.network.models

@kotlinx.serialization.Serializable
data class UserInfo(
    val name: String,
    val role: Int,
    val login: String,
    val defaultId: String
)
