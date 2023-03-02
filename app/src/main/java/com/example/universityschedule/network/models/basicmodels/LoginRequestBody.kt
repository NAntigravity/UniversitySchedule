package com.example.universityschedule.network.models.basicmodels

@kotlinx.serialization.Serializable
data class LoginRequestBody(
    val email: String,
    val password: String
)