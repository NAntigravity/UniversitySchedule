package com.example.universityschedule.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.universityschedule.network.ApiResponse
import com.example.universityschedule.network.BaseViewModel
import com.example.universityschedule.network.CoroutinesErrorHandler
import com.example.universityschedule.network.models.basicmodels.Room
import com.example.universityschedule.network.models.dropdownlists.RoomList

class SelectRoomViewModel : BaseViewModel() {
    private val coroutinesErrorHandler = object : CoroutinesErrorHandler {
        override fun onError(message: String) {
            Log.d("!", message)
        }
    }

    private val listInfoRepository: InformationTestRepository = InformationTestRepository()
    //private val listInfoRepository: InformationRepository = InformationRepository()

    var data = MutableLiveData<ApiResponse<RoomList>>()
    private var rooms = ArrayList<Room>()

    val suggestions = ArrayList<Room>()

    fun getRoomsByBuilding(buildingId: String) {
        data = MutableLiveData<ApiResponse<RoomList>>()
        baseRequest(data, coroutinesErrorHandler, listInfoRepository.getRoomsInBuilding(buildingId))
    }

    private fun copyAllRoomsToSuggestion() {
        for (room in rooms) {
            suggestions.add(room)
        }
    }

    fun saveRooms(data: RoomList) {
        for (room in data.rooms) {
            rooms.add(room)
        }
        copyAllRoomsToSuggestion()
    }
}
