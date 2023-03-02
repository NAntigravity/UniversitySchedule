package com.example.universityschedule.network.models

import com.example.appfortests.retrofit.models.basicmodels.*
import com.example.universityschedule.network.models.basicmodels.Building
import com.example.universityschedule.network.models.basicmodels.Group
import com.example.universityschedule.network.models.basicmodels.Subject
import com.example.universityschedule.network.models.basicmodels.Teacher

@kotlinx.serialization.Serializable
data class Lesson(
    val id: String,
    val timeslot: Int,
    val group: Group,
    val teacher: Teacher,
    val room: Room,
    val building: Building,
    val subject: Subject,
)
