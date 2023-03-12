package com.example.universityschedule.network.models

@kotlinx.serialization.Serializable
data class LoginResponse(
    val accessToken: String,
    val refreshToken: String
)
