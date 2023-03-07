package com.example.universityschedule.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.universityschedule.network.ApiResponse
import com.example.universityschedule.network.BaseViewModel
import com.example.universityschedule.network.CoroutinesErrorHandler
import com.example.universityschedule.network.apiRequestFlow
import com.example.universityschedule.network.models.basicmodels.Room
import com.example.universityschedule.network.models.dropdownlists.RoomList
import kotlinx.coroutines.delay
import retrofit2.Response

class SelectRoomViewModel: BaseViewModel() {
    private val coroutinesErrorHandler = object : CoroutinesErrorHandler {
        override fun onError(message: String) {
            Log.d("!", message)
        }
    }

    //private val listInfoApi: ListInfoApi = Network.getListInfoApi()
    private val testListInfoApi: RoomsTestApi = RoomsTestApi()

    var data = MutableLiveData<ApiResponse<RoomList>>()
    private var rooms = ArrayList<Room>()

    val suggestions = ArrayList<Room>()

    fun getRoomsByBuilding(buildingId: String){
        data = MutableLiveData<ApiResponse<RoomList>>()
        baseRequest(data, coroutinesErrorHandler, apiRequestFlow { testListInfoApi.getRoomsInBuilding(buildingId) } )
    }

    private fun copyAllRoomsToSuggestion(){
        for (room in rooms){
            suggestions.add(room)
        }
    }

    fun saveRooms(data: RoomList) {
        for (room in data.rooms){
            rooms.add(room)
        }
        copyAllRoomsToSuggestion()
    }
}

class RoomsTestApi {
    suspend fun getRoomsInBuilding(buildingId: String): Response<RoomList>{
        delay(3000L)

        val rooms = RoomList(rooms = listOf(Room(id="2", number = "1"),Room(id="2", number = "2"),Room(id="2", number = "3"),Room(id="2", number = "4"),Room(id="2", number = "202"),Room(id="2", number = "222"),Room(id="2", number = "2222"),Room(id="2", number = "222222"),Room(id="2", number = "300"),Room(id="2", number = "301"),Room(id="2", number = "302"),Room(id="2", number = "303"),Room(id="2", number = "304"),))

        return Response.success(rooms)
    }
}
