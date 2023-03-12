package com.example.universityschedule.network.models.basicmodels

@kotlinx.serialization.Serializable
data class RegisterRequestBody(
    val name: String,
    val email: String,
    val password: String
)
