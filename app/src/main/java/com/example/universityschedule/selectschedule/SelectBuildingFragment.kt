package com.example.universityschedule.selectschedule

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.universityschedule.R
import com.example.universityschedule.network.ApiResponse
import com.example.universityschedule.network.models.basicmodels.Building
import com.example.universityschedule.viewmodels.SelectBuildingViewModel

class SelectBuildingFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private var adapter: SelectAdapter? = null

    private val viewModel by lazy { ViewModelProvider(this)[SelectBuildingViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.select_schedule, container, false)
        recyclerView = view.findViewById(R.id.suggestion_list)
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = SelectAdapter(viewModel.suggestions)
        recyclerView.adapter = adapter

        view.findViewById<TextView>(R.id.title).text = resources.getText(R.string.buildings)
        view.findViewById<EditText>(R.id.enterData).hint =
            resources.getText(R.string.enter_building_number)

        updateUi(view.findViewById(R.id.progressBar))
        setTextWatchers(view.findViewById(R.id.enterData))
        return view
    }

    private fun setTextWatchers(editText: EditText) {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(
                sequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                viewModel.filterBy(sequence.toString())
                adapter?.notifyDataSetChanged()
            }

            override fun afterTextChanged(sequence: Editable?) {
            }
        }

        val doneClickListener =
            OnEditorActionListener { text, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    text.clearFocus()
                }
                false
            }

        editText.setOnEditorActionListener(doneClickListener)
        editText.addTextChangedListener(textWatcher)
    }

    private fun updateUi(progressBar: ProgressBar) {
        viewModel.data.observe(viewLifecycleOwner) {
            when (it) {
                ApiResponse.Loading -> progressBar.visibility = View.VISIBLE
                is ApiResponse.Failure -> {
                    Log.d("!", "Fail")
                    progressBar.visibility = View.GONE
                }
                is ApiResponse.Success -> {
                    viewModel.saveData(it.data)
                    progressBar.visibility = View.GONE
                    adapter?.notifyDataSetChanged()
                }
            }
        }
    }


    private inner class SuggestionHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
        private lateinit var data: Building
        private val textView: TextView = itemView.findViewById(R.id.select_item)

        fun bind(data: Building) {
            this.data = data
            textView.text = data.title
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            Log.d("!", "Clicked on ${textView.text}!")
        }
    }

    private inner class SelectAdapter(var suggestions: List<Building>) :
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
        fun newInstance(): SelectBuildingFragment {
            val newFragment = SelectBuildingFragment()

            return newFragment
        }
    }

}