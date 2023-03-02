package com.example.universityschedule.network.models.basicmodels

import com.google.gson.annotations.SerializedName

@kotlinx.serialization.Serializable
data class LoginRequestBody(
    @SerializedName("email_address")
    val email: String,
    val password: String
)