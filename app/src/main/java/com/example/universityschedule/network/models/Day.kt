package com.example.universityschedule.network.models

import kotlinx.datetime.LocalDate

@kotlinx.serialization.Serializable
data class Day(
    val lessons: List<Lesson>,
    val date: LocalDate,
    val dayOfTheWeek: Int
)
