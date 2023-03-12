package com.example.universityschedule

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.universityschedule.network.Network
import com.example.universityschedule.network.models.Lesson
import com.example.universityschedule.network.models.enumconverters.EnumConverter

class MainFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private val enumConverter = EnumConverter
    private lateinit var toMenuButton: ImageButton
    private inner class SuggestionHolder(view: View) : RecyclerView.ViewHolder(view){
        private lateinit var data: Lesson
        private val discipline: TextView = itemView.findViewById(R.id.discipline)
        private val building: TextView = itemView.findViewById(R.id.location_building)
        private val room: TextView = itemView.findViewById(R.id.location_room)
        private val teacher: TextView = itemView.findViewById(R.id.teacher_name)
        private val startTime: TextView = itemView.findViewById(R.id.lesson_start_time)
        private val endTime: TextView = itemView.findViewById(R.id.lesson_end_time)

        fun bind(data: Lesson) {
            this.data = data
            discipline.text = data.subject.toString()
            building.text = "Корпус № " + data.building.toString() + ","
            room.text = data.room.toString()
            teacher.text = data.teacher.toString()
            startTime.text = enumConverter.getTime(data.timeslot).first
            endTime.text = enumConverter.getTime(data.timeslot).second
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.main_frame, container, false)

        val roomId = requireArguments().getString("roomId")
        val groupId = requireArguments().getString("groupId")
        val teacherId = requireArguments().getString("teacherId")

        toMenuButton = view.findViewById(R.id.toMenuButton)

        toMenuButton.setOnClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionMainFrameToMenuFrame(
                    Network.userAuthorized
                )
            )
        }

        return view
    }
}