package com.example.universityschedule.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.universityschedule.TroubleShooting
import com.example.universityschedule.network.ApiResponse
import com.example.universityschedule.network.BaseViewModel
import com.example.universityschedule.network.models.basicmodels.Teacher
import com.example.universityschedule.network.repository.InformationRepository
import kotlinx.coroutines.launch

class SelectTeacherViewModel : BaseViewModel() {
    private val listInfoRepository: InformationRepository = InformationRepository()

    val data = MutableLiveData<ApiResponse<List<Teacher>>>()
    private var teachers = ArrayList<Teacher>()

    val suggestions = ArrayList<Teacher>()

    init {
        viewModelScope.launch {
            baseRequest(
                data,
                TroubleShooting.coroutinesErrorHandler,
                listInfoRepository.getTeachers()
            )
        }
    }

    fun filterBy(filter: String) {
        suggestions.clear()
        if (filter.isNotBlank()) {
            for (teacher in teachers) {
                if (teacher.name.contains(filter, ignoreCase = true)) {
                    suggestions.add(teacher)
                }
            }
        } else {
            copyAllTeachersToSuggestion()
        }
    }

    private fun copyAllTeachersToSuggestion() {
        for (teacher in teachers) {
            suggestions.add(teacher)
        }
    }

    fun saveData(data: List<Teacher>) {
        for (teacher in data) {
            teachers.add(teacher)
        }
        copyAllTeachersToSuggestion()
    }
}