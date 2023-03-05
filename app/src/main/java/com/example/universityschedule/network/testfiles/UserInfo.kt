package com.example.universityschedule.network.testfiles

@kotlinx.serialization.Serializable
data class idEmail(
    val _id: String,
    val email_address: String
)

@kotlinx.serialization.Serializable
data class UserInfo(
    val data: idEmail,
    val message: String
)
