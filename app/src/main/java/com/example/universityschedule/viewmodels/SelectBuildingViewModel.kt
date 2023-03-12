package com.example.universityschedule.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.universityschedule.TroubleShooting
import com.example.universityschedule.network.ApiResponse
import com.example.universityschedule.network.BaseViewModel
import com.example.universityschedule.network.apiRequestFlow
import com.example.universityschedule.network.models.basicmodels.Building
import com.example.universityschedule.network.models.basicmodels.Group
import com.example.universityschedule.network.models.basicmodels.Room
import com.example.universityschedule.network.models.basicmodels.Teacher
import com.example.universityschedule.network.models.dropdownlists.BuildingsList
import com.example.universityschedule.network.models.dropdownlists.GroupsList
import com.example.universityschedule.network.models.dropdownlists.RoomList
import com.example.universityschedule.network.models.dropdownlists.TeachersList
import com.example.universityschedule.network.repository.InformationRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response

class SelectBuildingViewModel : BaseViewModel() {

    //private val listInfoRepository: InformationTestRepository = InformationTestRepository()
    private val listInfoRepository: InformationRepository = InformationRepository()

    val data = MutableLiveData<ApiResponse<List<Building>>>()
    private var buildings = ArrayList<Building>()

    val suggestions = ArrayList<Building>()

    init {
        viewModelScope.launch {
            baseRequest(
                data,
                TroubleShooting.coroutinesErrorHandler,
                listInfoRepository.getBuildings()
            )
        }
    }

    fun filterBy(filter: String) {
        suggestions.clear()
        if (filter.isNotBlank()) {
            for (building in buildings) {
                if (building.title.contains(filter, ignoreCase = true)) {
                    suggestions.add(building)
                }
            }
        } else {
            copyAllBuildingsToSuggestion()
        }
    }

    private fun copyAllBuildingsToSuggestion() {
        suggestions.clear()
        for (teacher in buildings) {
            suggestions.add(teacher)
        }
    }

    fun saveData(data: List<Building>) {
        buildings.clear()
        for (building in data) {
            buildings.add(building)
        }
        copyAllBuildingsToSuggestion()
    }

}