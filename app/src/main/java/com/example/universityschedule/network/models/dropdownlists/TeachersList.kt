package com.example.universityschedule.network.models.dropdownlists

import com.example.universityschedule.network.models.basicmodels.Teacher

@kotlinx.serialization.Serializable
data class TeachersList(
    val teachers: List<Teacher>
)
