package com.example.universityschedule

import android.app.Application
import android.content.Context

class MainApplication: Application() {
    init {
        instance = this
    }

    companion object {

        private var instance: MainApplication? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }

        const val AccessToken: String = "access_token"
        const val RefreshToken: String = "refresh_token"

        const val UserId: String = "user_id"
        const val UserRole: String = "user_role"
        const val UserName: String = "user_name"
        const val StudentGroup: String = "student_group"

        const val TeachersFragment: String = "select_teacher"
        const val GroupsFragment: String = "select_group"
        const val BuildingsFragment: String = "select_building"
    }

}