package com.example.universityschedule.mainframe

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.universityschedule.R
import com.example.universityschedule.network.models.Lesson
import com.example.universityschedule.network.models.enumconverters.EnumConverter

class MainFrameFragment: Fragment() {
    private lateinit var recyclerView: RecyclerView
    private val enumConverter = EnumConverter
    private inner class SuggestionHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
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

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            Log.d("!", "Clicked on ${discipline.text}!")
        }
    }
}