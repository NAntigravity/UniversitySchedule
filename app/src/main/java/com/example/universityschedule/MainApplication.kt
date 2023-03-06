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

        const val TeachersFragment: String = "select_teacher"
        const val GroupsFragment: String = "select_group"
        const val BuildingsFragment: String = "select_building"


    }

}