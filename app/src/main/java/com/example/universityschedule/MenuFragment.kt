package com.example.universityschedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment

class MenuFragment: Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.menu, container, false)
        val authorizedUser = requireArguments().getBoolean("authorizedUser")

        val profileBlock: ConstraintLayout = view.findViewById(R.id.profile)
        val myScheduleBlock: ConstraintLayout = view.findViewById(R.id.my_schedule)
        val backToScheduleBlock: ConstraintLayout = view.findViewById(R.id.backToSchedule)


        val groupScheduleBlock: ConstraintLayout = view.findViewById(R.id.groups)
        val roomScheduleBlock: ConstraintLayout = view.findViewById(R.id.rooms)
        val teacherScheduleBlock: ConstraintLayout = view.findViewById(R.id.teachers)


        if (!authorizedUser){
            profileBlock.visibility = View.INVISIBLE
            myScheduleBlock.visibility = View.INVISIBLE
            backToScheduleBlock.visibility = View.INVISIBLE
        }

        // накинуть onClickListener для навигации, или это через граф делается ¯\_(ツ)_/¯

        return view
    }



    companion object{
        fun newInstance(authorizedUser: Boolean): MenuFragment{
            val args = Bundle()
            args.putBoolean("authorizedUser", authorizedUser)
            val view = MenuFragment()
            view.arguments = args
            return view
        }
    }
}