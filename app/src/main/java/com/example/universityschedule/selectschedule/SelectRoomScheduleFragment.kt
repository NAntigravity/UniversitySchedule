package com.example.universityschedule.selectschedule

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.universityschedule.R
import com.example.universityschedule.network.ApiResponse
import com.example.universityschedule.network.models.basicmodels.Room
import com.example.universityschedule.viewmodels.SelectRoomViewModel

class SelectRoomScheduleFragment: Fragment() {

    private lateinit var recyclerView: RecyclerView
    private var adapter: SelectAdapter? = null

    private val viewModel by lazy { ViewModelProvider(this)[SelectRoomViewModel::class.java] }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val buildingId = requireArguments().getString("buildingId") as String

        val view = inflater.inflate(R.layout.select_room, container, false)
        recyclerView = view.findViewById(R.id.suggestion_list)
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = SelectAdapter(viewModel.suggestions)
        recyclerView.adapter = adapter

        val textView: TextView = view.findViewById(R.id.building)
        textView.text = "${buildingId} " + resources.getText(R.string.building)

        updateUi(view.findViewById(R.id.progressBar), buildingId)

        return view
    }

    private fun updateUi(progressBar: ProgressBar, buildingId: String) {
        viewModel.getRoomsByBuilding(buildingId)
        viewModel.data.observe(viewLifecycleOwner) {
            when (it) {
                ApiResponse.Loading -> progressBar.visibility = View.VISIBLE
                is ApiResponse.Failure -> {
                    Log.d("!", "Fail")
                    progressBar.visibility = View.GONE
                }
                is ApiResponse.Success -> {
                    viewModel.saveRooms(it.data)
                    progressBar.visibility = View.GONE
                    adapter?.notifyDataSetChanged()
                }
            }
        }
    }

    private inner class SuggestionHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
        private lateinit var data: Room
        private val textView: TextView = itemView.findViewById(R.id.select_item)

        fun bind(data: Room) {
            this.data = data
            textView.text = data.number
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            Log.d("!", "Clicked on ${textView.text}!")
        }
    }

    private inner class SelectAdapter(var suggestions: List<Room>) :
        RecyclerView.Adapter<SuggestionHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionHolder {
            val view = layoutInflater.inflate(R.layout.select_item, parent, false)
            return SuggestionHolder(view)
        }

        override fun onBindViewHolder(holder: SuggestionHolder, position: Int) {
            val suggestion = suggestions[position]
            holder.bind(suggestion)
        }

        override fun getItemCount() = suggestions.size
    }


    companion object {
        fun newInstance(buildingId: String): SelectRoomScheduleFragment {
            val args = Bundle()
            args.putString("buildingId", buildingId)

            val newFragment = SelectRoomScheduleFragment()
            newFragment.arguments = args
            return newFragment
        }
    }
}