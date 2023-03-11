package com.example.universityschedule.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.universityschedule.network.ApiResponse
import com.example.universityschedule.network.BaseViewModel
import com.example.universityschedule.network.CoroutinesErrorHandler
import com.example.universityschedule.network.models.basicmodels.Group
import com.example.universityschedule.network.models.dropdownlists.GroupsList
import kotlinx.coroutines.launch

class SelectGroupViewModel : BaseViewModel() {
    private val coroutinesErrorHandler = object : CoroutinesErrorHandler {
        override fun onError(message: String) {
            Log.d("!", message)
        }
    }

    private val listInfoRepository: InformationTestRepository = InformationTestRepository()
    //private val listInfoRepository: InformationRepository = InformationRepository()

    val data = MutableLiveData<ApiResponse<GroupsList>>()
    private var groups = ArrayList<Group>()

    val suggestions = ArrayList<Group>()

    init {
        viewModelScope.launch {
            baseRequest(data, coroutinesErrorHandler, listInfoRepository.getGroups())
        }
    }

    fun filterBy(filter: String) {
        suggestions.clear()
        if (filter.isNotBlank()) {
            for (building in groups) {
                if (building.number.contains(filter, ignoreCase = true)) {
                    suggestions.add(building)
                }
            }
        } else {
            copyAllGroupsToSuggestion()
        }
    }

    private fun copyAllGroupsToSuggestion() {
        for (group in groups) {
            suggestions.add(group)
        }
    }

    fun saveData(data: GroupsList) {
        for (group in data.groups) {
            groups.add(group)
        }
        copyAllGroupsToSuggestion()
    }
}