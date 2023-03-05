package com.example.universityschedule.network.repository

import com.example.universityschedule.network.Network
import com.example.universityschedule.network.api.ScheduleApi
import com.example.universityschedule.network.apiRequestFlow
import kotlinx.datetime.LocalDate

class ScheduleRepository {
    private val scheduleApi: ScheduleApi = Network.getScheduleApi()

    fun getScheduleOnWeek(
        date: LocalDate,
        roomId: String? = null,
        groupId: String? = null,
        teacherId: String? = null,
        subjectId: String? = null,
    ) = apiRequestFlow {
        scheduleApi.getScheduleOnWeek(date, roomId, groupId, teacherId, subjectId)
    }

}