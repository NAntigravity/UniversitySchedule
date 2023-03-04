package com.example.universityschedule

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SelectScheduleFragment: Fragment() {

    private lateinit var recyclerView: RecyclerView
    private var adapter: CrimeAdapter? = null

    private var allData = arrayListOf("Тестов тест Тестович", "Тестов тест Тестович", "Тестов тест Тестович", "Тестов тест Тестович", "Тестов тест Тестович", "Тестов тест Тестович", "Тестов тест Тестович", "Тестов тест Тестович", "Тестов тест Тестович", "Тестов тест Тестович", "Тестов тест Тестович", "Тестов тест Тестович", "Тестов тест Тестович", "Тестов тест Тестович", "Тестов тест Тестович")
    private var suggestions = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.select_teacher, container, false)
        recyclerView = view.findViewById(R.id.suggestion_list)
        recyclerView.layoutManager = LinearLayoutManager(context)

        updateUi()
        setTextWatcher(view.findViewById(R.id.enterData))
        return view
    }

    private fun setTextWatcher(editText: EditText) {
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
                suggestions.clear()
                if (sequence.toString().isNotBlank()){
                    for (text in allData){
                        if (text.contains(sequence.toString(), ignoreCase = true)){
                            suggestions.add(text)
                        }
                    }
                }
                else{
                    copyAllDataToSuggestions()
                }
                adapter?.notifyDataSetChanged()

            }

            override fun afterTextChanged(sequence: Editable?) {
            }
        }
        editText.addTextChangedListener(textWatcher)
    }

    private fun updateUi() {
        copyAllDataToSuggestions()
        adapter = CrimeAdapter(suggestions)
        recyclerView.adapter = adapter
    }

    private fun copyAllDataToSuggestions(){
        allData.forEach {
            suggestions.add(it)
        }
    }

    private inner class SuggestionHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener{
        private lateinit var data: String
        private val textView: TextView = itemView.findViewById(R.id.select_item)

        fun bind(data: String) {
            this.data = data
            textView.text = data
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            TODO("Not yet implemented")
        }
    }

    private inner class CrimeAdapter(var suggestions: List<String>): RecyclerView.Adapter<SuggestionHolder>(){
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

    companion object{
        fun newInstance(): SelectScheduleFragment{
            return SelectScheduleFragment()
        }
    }
}