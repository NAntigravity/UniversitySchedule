package com.example.universityschedule.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.universityschedule.network.*
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

class SelectBuildingViewModel: BaseViewModel() {
    private val coroutinesErrorHandler = object : CoroutinesErrorHandler {
        override fun onError(message: String) {
            Log.d("!", message)
        }
    }


    private val listInfoRepository: InformationTestRepository = InformationTestRepository()
    //private val listInfoRepository: InformationRepository = InformationRepository()

    val data = MutableLiveData<ApiResponse<BuildingsList>>()
    private var buildings = ArrayList<Building>()

    val suggestions = ArrayList<Building>()

    init{
        viewModelScope.launch {
            baseRequest(data, coroutinesErrorHandler, listInfoRepository.getBuildings() )
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


class InformationTestRepository {
    private val roomApi: RoomTestApi = RoomTestApi()
    private val infoApi: ListInfoTestApi = ListInfoTestApi()

    fun getRoomsInBuilding(buildingId: String) = apiRequestFlow {
        roomApi.getRoomsInBuilding(buildingId)
    }

    fun getGroups() = apiRequestFlow {
        infoApi.getGroups()
    }

    fun getTeachers() = apiRequestFlow {
        infoApi.getTeachers()
    }

    fun getBuildings() = apiRequestFlow {
        infoApi.getBuildings()
    }
}
class RoomTestApi{
    suspend fun getRoomsInBuilding(buildingId: String): Response<RoomList>{
        delay(3000L)

        val rooms = RoomList(rooms = listOf(
            Room(id="2", number = "1"),
            Room(id="2", number = "2"),
            Room(id="2", number = "3"),
            Room(id="2", number = "4"),
            Room(id="2", number = "202"),
            Room(id="2", number = "222"),
            Room(id="2", number = "2222"),
            Room(id="2", number = "222222"),
            Room(id="2", number = "300"),
            Room(id="2", number = "301"),
            Room(id="2", number = "302"),
            Room(id="2", number = "303"),
            Room(id="2", number = "304"),))

        return Response.success(rooms)
    }
}

class ListInfoTestApi {

    suspend fun getGroups(): Response<GroupsList> {
        delay(3000L)

        val groupList = GroupsList(
            listOf(
                Group(id="123", number = "972102"),
                Group(id="123", number = "972102"),
                Group(id="123", number = "972102"),
                Group(id="123", number = "972102"),
                Group(id="123", number = "972102"),
                Group(id="123", number = "972102"),
                Group(id="123", number = "972102"),)
        )

        return Response.success(groupList)
    }

    suspend fun getTeachers(): Response<TeachersList>{
        delay(3000L)

        val teacherList = TeachersList(
            teachers = listOf(
                Teacher(id = "12323", name = "Тест Тестов"), Teacher(id = "12323", name = "ТесЫФВт Тестов"),
                Teacher(id = "12323", name = "Твывест Тестов"),
                Teacher(id = "12323", name = "Тест Тестов"),
                Teacher(id = "12323", name = "Тест Тестов"),)
        )

        return Response.success(teacherList)
    }

    suspend fun getBuildings(): Response<BuildingsList>{
        delay(3000L)

        val buildingList = BuildingsList(
            buildings = listOf(Building(id="123", "1 корпус"),Building(id="123", "2 корпус"),Building(id="123", "3 корпус"),Building(id="123", "4 корпус"),)
        )

        return Response.success(buildingList)
    }
}
