package com.example.universityschedule.network.models

@kotlinx.serialization.Serializable
data class Week(
    val days: List<Day>
)
