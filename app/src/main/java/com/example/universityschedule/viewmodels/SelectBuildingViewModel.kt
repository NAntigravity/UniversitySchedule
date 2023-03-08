package com.example.universityschedule.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.universityschedule.network.*
import com.example.universityschedule.network.models.basicmodels.Building
import com.example.universityschedule.network.models.dropdownlists.BuildingsList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response

class SelectBuildingViewModel: BaseViewModel() {
    private val coroutinesErrorHandler = object : CoroutinesErrorHandler {
        override fun onError(message: String) {
            Log.d("!", message)
        }
    }

    //private val listInfoApi: ListInfoApi = Network.getListInfoApi()
    private val testListInfoApi: BuildingTestApi = BuildingTestApi()

    val data = MutableLiveData<ApiResponse<BuildingsList>>()
    private var buildings = ArrayList<Building>()

    val suggestions = ArrayList<Building>()

    init{
        viewModelScope.launch {
            baseRequest(data, coroutinesErrorHandler, apiRequestFlow { testListInfoApi.getBuildings() })
        }
    }

    fun filterBy(filter: String){
        suggestions.clear()
        if (filter.isNotBlank()){
            for (building in buildings){
                if (building.title.contains(filter, ignoreCase = true)){
                    suggestions.add(building)
                }
            }
        }
        else{
            copyAllBuildingsToSuggestion()
        }
    }

    private fun copyAllBuildingsToSuggestion(){
        for (teacher in buildings){
            suggestions.add(teacher)
        }
    }

     fun saveData(data: BuildingsList) {
        for (building in data.buildings){
            buildings.add(building)
        }
        copyAllBuildingsToSuggestion()
    }

}

class BuildingTestApi {
    suspend fun getBuildings(): Response<BuildingsList>{
        delay(3000L)

        val buildingList = BuildingsList(
            buildings = listOf(Building(id="123", "1 корпус"),Building(id="123", "2 корпус"),Building(id="123", "3 корпус"),Building(id="123", "4 корпус"),)
        )

        return Response.success(buildingList)
    }
}
