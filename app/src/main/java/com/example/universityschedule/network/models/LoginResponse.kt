package com.example.universityschedule.network.models

@kotlinx.serialization.Serializable
data class LoginResponse(
    val access_token: String,
    //val refresh_token: String
)
