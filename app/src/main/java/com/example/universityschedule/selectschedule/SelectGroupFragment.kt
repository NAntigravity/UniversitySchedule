package com.example.universityschedule.selectschedule

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import android.widget.TextView.OnEditorActionListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.universityschedule.R
import com.example.universityschedule.network.ApiResponse
import com.example.universityschedule.network.models.basicmodels.Group
import com.example.universityschedule.viewmodels.SelectGroupViewModel

class SelectGroupFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private var adapter: SelectAdapter? = null

    private val viewModel by lazy { ViewModelProvider(this)[SelectGroupViewModel::class.java] }

    private lateinit var backButton: ImageView

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

        view.findViewById<TextView>(R.id.title).text = resources.getText(R.string.groups)
        view.findViewById<EditText>(R.id.enterData).hint = resources.getText(R.string.enter_group_number)

        updateUi(view.findViewById(R.id.progressBar))
        setTextWatchers(view.findViewById(R.id.enterData))

        backButton = view.findViewById(R.id.backButton)
        backButton.setOnClickListener { findNavController().navigateUp() }
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
                    Log.d("!", it.data.size.toString())
                    Log.d("!", it.data.toString())
                    viewModel.saveData(it.data)
                    progressBar.visibility = View.GONE
                    adapter?.notifyDataSetChanged()
                }
            }
        }
    }


    private inner class SuggestionHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
        private lateinit var data: Group
        private val textView: TextView = itemView.findViewById(R.id.select_item)

        fun bind(data: Group) {
            this.data = data
            textView.text = data.name
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            findNavController().navigate(SelectGroupFragmentDirections.actionSelectGroupFrameToMainFrame(groupId = data.id))
        }
    }

    private inner class SelectAdapter(var suggestions: List<Group>) :
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
}