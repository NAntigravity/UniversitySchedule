package com.example.universityschedule.network.api
import com.example.universityschedule.network.models.Week
import kotlinx.datetime.LocalDate
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*

interface ScheduleApi {

    @GET("schedule/{date}")
    suspend fun getScheduleOnWeek(
        @Path("date") date: LocalDate,
        @Query("roomID") roomId: String? = null,
        @Query("groupID") groupId: String? = null,
        @Query("teacherID") teacherId: String? = null,
        @Query("subjectID") subjectId: String? = null,
    ): Response<Week>
}