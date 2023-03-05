package com.example.universityschedule.network.api
import com.example.universityschedule.network.models.Week
import kotlinx.datetime.LocalDate
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*

interface ScheduleApi {

    @GET("lessons/{date}")
    suspend fun getScheduleOnWeek(
        @Path("date") date: LocalDate,
        @Query("roomId") roomId: String? = null,
        @Query("groupId") groupId: String? = null,
        @Query("teacherId") teacherId: String? = null,
        @Query("subjectId") subjectId: String? = null,
    ): Response<Week>
}