package com.example.universityschedule.viewmodels

import androidx.lifecycle.MutableLiveData
import com.example.universityschedule.TroubleShooting
import com.example.universityschedule.network.ApiResponse
import com.example.universityschedule.network.BaseViewModel
import com.example.universityschedule.network.models.basicmodels.Room
import com.example.universityschedule.network.repository.InformationRepository

class SelectRoomViewModel : BaseViewModel() {

    private val listInfoRepository: InformationRepository = InformationRepository()

    var data = MutableLiveData<ApiResponse<List<Room>>>()
    private var rooms = ArrayList<Room>()

    val suggestions = ArrayList<Room>()

    fun getRoomsByBuilding(buildingId: String) {
        data = MutableLiveData<ApiResponse<List<Room>>>()
        baseRequest(data, TroubleShooting.coroutinesErrorHandler, listInfoRepository.getRoomsInBuilding(buildingId))
    }

    private fun copyAllRoomsToSuggestion() {
        for (room in rooms) {
            suggestions.add(room)
        }
    }

    fun saveRooms(data: List<Room>) {
        for (room in data) {
            rooms.add(room)
        }
        copyAllRoomsToSuggestion()
    }
}
