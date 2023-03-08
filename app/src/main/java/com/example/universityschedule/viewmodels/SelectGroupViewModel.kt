package com.example.universityschedule.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.universityschedule.network.ApiResponse
import com.example.universityschedule.network.BaseViewModel
import com.example.universityschedule.network.CoroutinesErrorHandler
import com.example.universityschedule.network.apiRequestFlow
import com.example.universityschedule.network.models.basicmodels.Group
import com.example.universityschedule.network.models.dropdownlists.BuildingsList
import com.example.universityschedule.network.models.dropdownlists.GroupsList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response

class SelectGroupViewModel: BaseViewModel() {
    private val coroutinesErrorHandler = object : CoroutinesErrorHandler {
        override fun onError(message: String) {
            Log.d("!", message)
        }
    }

    //private val listInfoApi: ListInfoApi = Network.getListInfoApi()
    private val testListInfoApi: GroupTestApi = GroupTestApi()

    val data = MutableLiveData<ApiResponse<GroupsList>>()
    private var groups = ArrayList<Group>()

    val suggestions = ArrayList<Group>()

    init{
        viewModelScope.launch {
            baseRequest(data, coroutinesErrorHandler, apiRequestFlow { testListInfoApi.getGroups() })
        }
    }

    fun filterBy(filter: String){
        suggestions.clear()
        if (filter.isNotBlank()){
            for (building in groups){
                if (building.number.contains(filter, ignoreCase = true)){
                    suggestions.add(building)
                }
            }
        }
        else{
            copyAllGroupsToSuggestion()
        }
    }

    private fun copyAllGroupsToSuggestion(){
        for (group in groups){
            suggestions.add(group)
        }
    }

    fun saveData(data: GroupsList) {
        for (group in data.groups){
            groups.add(group)
        }
        copyAllGroupsToSuggestion()
    }
}
class GroupTestApi {
    suspend fun getGroups(): Response<GroupsList> {
        delay(3000L)

        val groupList = GroupsList(
            listOf(Group(id="123", number = "972102"),Group(id="123", number = "972102"),Group(id="123", number = "972102"),Group(id="123", number = "972102"),Group(id="123", number = "972102"),Group(id="123", number = "972102"),Group(id="123", number = "972102"),)
            )

        return Response.success(groupList)
    }
}