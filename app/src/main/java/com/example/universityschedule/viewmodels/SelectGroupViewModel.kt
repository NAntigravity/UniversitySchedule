package com.example.universityschedule.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.universityschedule.TroubleShooting
import com.example.universityschedule.network.ApiResponse
import com.example.universityschedule.network.BaseViewModel
import com.example.universityschedule.network.models.basicmodels.Group
import com.example.universityschedule.network.repository.InformationRepository
import kotlinx.coroutines.launch

class SelectGroupViewModel : BaseViewModel() {

    private val listInfoRepository: InformationRepository = InformationRepository()

    val data = MutableLiveData<ApiResponse<List<Group>>>()
    private var groups = ArrayList<Group>()

    val suggestions = ArrayList<Group>()

    init {
        viewModelScope.launch {
            baseRequest(data, TroubleShooting.coroutinesErrorHandler, listInfoRepository.getGroups())
        }
    }

    fun filterBy(filter: String) {
        suggestions.clear()
        if (filter.isNotBlank()) {
            for (building in groups) {
                if (building.name.contains(filter, ignoreCase = true)) {
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

    fun saveData(data: List<Group>) {
        for (group in data) {
            groups.add(group)
        }
        copyAllGroupsToSuggestion()
    }
}