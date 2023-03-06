package com.example.universityschedule.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.universityschedule.network.models.basicmodels.Teacher
import com.example.universityschedule.network.models.dropdownlists.TeachersList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response

class SelectTeacherViewModel: BaseViewModel() {
    private val coroutinesErrorHandler = object : CoroutinesErrorHandler {
        override fun onError(message: String) {
            Log.d("!", message)
        }
    }

    //private val listInfoApi: ListInfoApi = Network.getListInfoApi()
    private val testListInfoApi: TeachersTestApi = TeachersTestApi()

    val data = MutableLiveData<ApiResponse<TeachersList>>()
    private var teachers = ArrayList<String>()

    val suggestions = ArrayList<String>()

    init{
        viewModelScope.launch {
            baseRequest(data, coroutinesErrorHandler, apiRequestFlow { testListInfoApi.getTeachers() })
        }
    }

    fun filterBy(filter: String){
        suggestions.clear()
        if (filter.isNotBlank()){
            for (teacher in teachers){
                if (teacher.contains(filter, ignoreCase = true)){
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

    fun saveTeachers(data: TeachersList) {
        for (teacher in data.teachers){
            teachers.add(teacher.name)
        }
        copyAllTeachersToSuggestion()
    }
}

class TeachersTestApi {
    suspend fun getTeachers(): Response<TeachersList>{
        delay(3000L)

        val teacherList = TeachersList(
            teachers = listOf(Teacher(id = "12323", name = "Тест Тестов"), Teacher(id = "12323", name = "ТесЫФВт Тестов"),Teacher(id = "12323", name = "Твывест Тестов"),Teacher(id = "12323", name = "Тест Тестов"),Teacher(id = "12323", name = "Тест Тестов"),)
        )

        return Response.success(teacherList)
    }
}
