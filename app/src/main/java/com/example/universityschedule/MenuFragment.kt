package com.example.universityschedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.universityschedule.network.ApiResponse
import com.example.universityschedule.network.Network
import com.example.universityschedule.network.models.enumconverters.EnumConverter
import com.example.universityschedule.viewmodels.MenuViewModel

class MenuFragment : Fragment() {

    private val viewModel by lazy { ViewModelProvider(this)[MenuViewModel::class.java] }

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
        val buildingScheduleBlock: ConstraintLayout = view.findViewById(R.id.rooms)
        val teacherScheduleBlock: ConstraintLayout = view.findViewById(R.id.teachers)


        if (!authorizedUser) {
            profileBlock.visibility = View.INVISIBLE
            myScheduleBlock.visibility = View.INVISIBLE
            backToScheduleBlock.visibility = View.INVISIBLE
        } else {
            profileBlock.setOnClickListener { findNavController().navigate(MenuFragmentDirections.actionMenuFrameToProfileFrame()) }
            myScheduleBlock.setOnClickListener {
                val userId = Network.getSharedPrefs(MainApplication.UserId)
                when (EnumConverter.getRole(Network.getSharedPrefs(MainApplication.UserRole))) {
                    "teacher" -> findNavController().navigate(
                        MenuFragmentDirections.actionMenuFrameToMainFrame(
                            teacherId = userId
                        )
                    )
                    "student" -> findNavController().navigate(
                        MenuFragmentDirections.actionMenuFrameToMainFrame(
                            groupId = userId
                        )
                    )
                }
            }
            backToScheduleBlock.setOnClickListener { findNavController().navigateUp() }

            viewModel.userData.observe(viewLifecycleOwner) {
                when (it) {
                    ApiResponse.Loading -> { }
                    is ApiResponse.Success -> {
                        Network.updateSharedPrefs(MainApplication.UserId, it.data.defaultId)
                        Network.updateSharedPrefs(MainApplication.UserRole, it.data.role.toString())
                        Network.updateSharedPrefs(MainApplication.UserName, it.data.login)
                    }
                    is ApiResponse.Failure -> { }
                }
            }
        }

        groupScheduleBlock.setOnClickListener { findNavController().navigate(MenuFragmentDirections.actionMenuFrameToSelectGroupFrame()) }
        buildingScheduleBlock.setOnClickListener {
            findNavController().navigate(
                MenuFragmentDirections.actionMenuFrameToSelectBuildingMenuFrame()
            )
        }
        teacherScheduleBlock.setOnClickListener {
            findNavController().navigate(
                MenuFragmentDirections.actionMenuFrameToSelectTeacherMenuFrame()
            )
        }

        return view
    }

}