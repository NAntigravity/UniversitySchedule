package com.example.universityschedule.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.universityschedule.network.ApiResponse
import com.example.universityschedule.network.BaseViewModel
import com.example.universityschedule.network.CoroutinesErrorHandler
import com.example.universityschedule.network.apiRequestFlow
import com.example.universityschedule.network.models.basicmodels.Teacher
import com.example.universityschedule.network.models.dropdownlists.TeachersList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response

class SelectTeacherViewModel(): BaseViewModel() {
    private val coroutinesErrorHandler = object : CoroutinesErrorHandler {
        override fun onError(message: String) {
            Log.d("!", message)
        }
    }

    private val listInfoRepository: InformationTestRepository = InformationTestRepository()
    //private val listInfoRepository: InformationRepository = InformationRepository()

    val data = MutableLiveData<ApiResponse<TeachersList>>()
    private var teachers = ArrayList<Teacher>()

    val suggestions = ArrayList<Teacher>()

    init{
        viewModelScope.launch {
            baseRequest(data, coroutinesErrorHandler, listInfoRepository.getTeachers())
        }
    }

    fun filterBy(filter: String){
        suggestions.clear()
        if (filter.isNotBlank()){
            for (teacher in teachers){
                if (teacher.name.contains(filter, ignoreCase = true)){
                    suggestions.add(teacher)
                }
            }
        }
        else{
            copyAllTeachersToSuggestion()
        }
    }

    private fun copyAllTeachersToSuggestion(){
        for (teacher in teachers){
            suggestions.add(teacher)
        }
    }

    fun saveData(data: TeachersList) {
        for (teacher in data.teachers){
            teachers.add(teacher)
        }
        copyAllTeachersToSuggestion()
    }
}