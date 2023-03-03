package com.example.universityschedule.network.models.dropdownlists

import com.example.universityschedule.network.models.basicmodels.Subject

@kotlinx.serialization.Serializable
data class SubjectsList(
    val subjects: List<Subject>
)
